// Variável para armazenar a ocorrência atual
let ocorrenciaAtual = null;

// Função para carregar opções dinâmicas nos filtros
async function carregarOpcoes() {
    try {
        // Carregar locais (obras)
        const obrasResponse = await fetch("/api/obras");
        const obras = await obrasResponse.json();
        const filtroObra = document.getElementById("filtro-obra");
        obras.forEach(obra => {
            const option = document.createElement("option");
            option.value = obra.obraid;
            option.textContent = obra.nome;
            filtroObra.appendChild(option);
        });

        // Carregar status
        const statusResponse = await fetch("/api/status-ocorrencia");
        const statusList = await statusResponse.json();
        const filtroStatus = document.getElementById("filtro-status");
        statusList.forEach(status => {
            const option = document.createElement("option");
            option.value = status.id;
            option.textContent = status.descricao;
            filtroStatus.appendChild(option);
        });

        // Carregar grupos
        const gruposResponse = await fetch("/api/grupo-ocorrencia");
        const grupos = await gruposResponse.json();
        const filtroGrupo = document.getElementById("filtro-grupo");
        grupos.forEach(grupo => {
            const option = document.createElement("option");
            option.value = grupo.grupoOcorrenciaId;
            option.textContent = grupo.grupoOcorrencia;
            filtroGrupo.appendChild(option);
        });
    } catch (error) {
        console.error("Erro ao carregar opções:", error);
    }
}

// Função para carregar ocorrências com filtros aplicados
async function carregarOcorrencias() {
    const spinner = document.getElementById("loading-spinner");
    const button = document.getElementById("btn-filtrar");

    try {
        // Exibir spinner
        spinner.style.display = "inline-block";
        button.disabled = true;

        // Coletar valores dos filtros
        const obraId = document.getElementById("filtro-obra").value;
        const statusId = document.getElementById("filtro-status").value;
        const grupoId = document.getElementById("filtro-grupo").value;
        const data = document.getElementById("filtro-data").value;
        const id = document.getElementById("filtro-id").value.trim();

        let url;
        if (id) {
            // Buscar diretamente pelo ID
            url = `/api/ocorrencias/${id}`;
        } else {
            // Construir URL com filtros
            const params = new URLSearchParams();
            if (obraId) params.append("obraId", obraId);
            if (statusId) params.append("statusId", statusId);
            if (grupoId) params.append("grupoId", grupoId);
            if (data) params.append("data", data);

            url = `/api/ocorrencias?${params.toString()}`;
        }

        // Chamar API
        const response = await fetch(url);
        if (!response.ok) throw new Error("Erro ao buscar ocorrências.");

        const ocorrencias = id ? [await response.json()] : await response.json();
        exibirOcorrencias(ocorrencias);
    } catch (error) {
        console.error("Erro ao carregar ocorrências:", error);
    } finally {
        // Ocultar spinner e habilitar botão
        spinner.style.display = "none";
        button.disabled = false;
    }
}

// Função para exibir ocorrências nos cards
function exibirOcorrencias(ocorrencias) {
    const container = document.getElementById("cards-container");
    const totalOcorrencias = document.getElementById("total-ocorrencias");

    // Atualizar total de ocorrências encontradas
    totalOcorrencias.textContent = `(${ocorrencias.length})`;

    // Limpar container
    container.innerHTML = "";

    if (ocorrencias.length === 0) {
        container.innerHTML = "<p>Nenhuma ocorrência encontrada.</p>";
    } else {
        ocorrencias.forEach(ocorrencia => {
            const card = document.createElement("div");
            card.className = "card";

            // Determinar a classe para o status
            let statusClass = "";
            switch (ocorrencia.statusOcorrencia.descricao) {
                case "Recebida":
                    statusClass = "status-recebida";
                    break;
                case "Em tratamento":
                    statusClass = "status-em-tratamento";
                    break;
                case "Finalizada":
                    statusClass = "status-finalizada";
                    break;
                default:
                    statusClass = "status-default";
            }

            // Determinar prazo com base no tratamento
            let prazoClass = "";
            let prazo = "";
            if (ocorrencia.statusOcorrencia.descricao !== "Finalizada") {
                prazo = ocorrencia.tratamentoOcorrencia || "Sem prazo definido";

                if (ocorrencia.tratamentoOcorrencia) {
                    if (ocorrencia.tratamentoOcorrencia.includes("Vencido")) {
                        prazoClass = "prazo-vencido";
                    } else if (ocorrencia.tratamentoOcorrencia.includes("Vence hoje")) {
                        prazoClass = "prazo-vence-hoje";
                    } else if (/(\d+) dias restantes/.test(prazo)) {
                        const diasRestantes = parseInt(prazo.match(/(\d+)/)[1]);
                        if (diasRestantes <= 7) {
                            prazoClass = "prazo-aviso";
                        } else {
                            prazoClass = "prazo-normal";
                        }
                    }
                }
            }

            // Determinar data exibida
            let dataExibida = "";
            if (ocorrencia.statusOcorrencia.descricao === "Finalizada") {
                dataExibida = ocorrencia.dataResolucao
                    ? `Resolvido em: ${ocorrencia.dataResolucao}`
                    : "Data de resolução não definida";
            } else {
                dataExibida = ocorrencia.dataAcordada
                    ? `Data Acordada: ${ocorrencia.dataAcordada}`
                    : "Data Acordada não definida";
            }

            // Adicionar gravidade da ocorrência
            const gravidade = ocorrencia.gravidade
                ? ocorrencia.gravidade.gravidade
                : "Gravidade não definida";

            card.innerHTML = `
                <p><strong>ID:</strong> ${ocorrencia.ocorrenciaId}</p>
                <p><strong>Local:</strong> ${ocorrencia.obra.nome}</p>
                <p><strong>Grupo:</strong> ${ocorrencia.grupoOcorrencia.grupoOcorrencia}</p>
                <p><strong>Gravidade:</strong> ${gravidade}</p>
                <p><strong>Data:</strong> ${ocorrencia.dataRegistro}</p>
                <p><strong>Status:</strong> <span class="status ${statusClass}">${ocorrencia.statusOcorrencia.descricao}</span></p>
                <p><strong>${dataExibida}</strong></p>
                ${prazo && ocorrencia.statusOcorrencia.descricao !== "Finalizada" ? `<p class="prazo ${prazoClass}">${prazo}</p>` : ""}
            `;
            card.addEventListener("click", () => abrirModal(ocorrencia));
            container.appendChild(card);
        });
    }
}




// Função para abrir modal de detalhes
function abrirModal(ocorrencia) {
    ocorrenciaAtual = ocorrencia;

    document.getElementById("modal-ocorrencia-id").textContent = ocorrencia.ocorrenciaId;
    document.getElementById("modal-ocorrencia-local").textContent = ocorrencia.obra.nome;
    document.getElementById("modal-ocorrencia-grupo").textContent = ocorrencia.grupoOcorrencia.grupoOcorrencia;
    document.getElementById("modal-ocorrencia-data").textContent = ocorrencia.dataRegistro;
    document.getElementById("modal-ocorrencia-status").textContent = ocorrencia.statusOcorrencia.descricao;
    document.getElementById("modal-ocorrencia-descricao").textContent = ocorrencia.ocorrencia;
    document.getElementById("modal-ocorrencia-detalhamento").textContent = ocorrencia.ocorrenciaDetalhada;

    document.getElementById("modal-ocorrencia").style.display = "flex";
}

// Função para fechar modal
function fecharModal() {
    document.getElementById("modal-ocorrencia").style.display = "none";
}

// Inicializar página
document.addEventListener("DOMContentLoaded", () => {
    carregarOpcoes();
    carregarOcorrencias();
});
