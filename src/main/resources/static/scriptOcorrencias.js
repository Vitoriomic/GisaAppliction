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

            url = `/api/ocorrencias/filtros?${params.toString()}`;
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

            // Determinar a classe para a gravidade
            let gravidadeClass = "";
            if (ocorrencia.gravidade) {
                switch (ocorrencia.gravidade.gravidade) {
                    case "Desvio":
                        gravidadeClass = "gravidade-desvio";
                        break;
                    case "Inconformidade Leve":
                        gravidadeClass = "gravidade-leve";
                        break;
                    case "Inconformidade Média":
                        gravidadeClass = "gravidade-media";
                        break;
                    case "Inconformidade Grave":
                        gravidadeClass = "gravidade-grave";
                        break;
                    default:
                        gravidadeClass = "gravidade-default";
                }
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
                <p><strong>Gravidade:</strong> <span class="${gravidadeClass}">${gravidade}</span></p>
                <p><strong>Data de Registro:</strong> ${ocorrencia.dataRegistro}</p>
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

    // Campos comuns
    document.getElementById("modal-ocorrencia-id").textContent = ocorrencia.ocorrenciaId;
    document.getElementById("modal-ocorrencia-local").textContent = ocorrencia.obra.nome;
    document.getElementById("modal-ocorrencia-grupo").textContent = ocorrencia.grupoOcorrencia.grupoOcorrencia;

    // Gravidade com cores
    const gravidadeElement = document.getElementById("modal-ocorrencia-gravidade");
    gravidadeElement.textContent = ocorrencia.gravidade.gravidade;
    switch (ocorrencia.gravidade.gravidade) {
        case "Desvio":
            gravidadeElement.style.color = "gray";
            break;
        case "Inconformidade Leve":
            gravidadeElement.style.color = "lightorange";
            break;
        case "Inconformidade Média":
            gravidadeElement.style.color = "orange";
            break;
        case "Inconformidade Grave":
            gravidadeElement.style.color = "red";
            break;
        default:
            gravidadeElement.style.color = "black";
    }

    // Supervisor
    document.getElementById("modal-ocorrencia-supervisor").textContent = ocorrencia.supervisor.nome;

    // Datas
    document.getElementById("modal-ocorrencia-data-registro").textContent = ocorrencia.dataRegistro;
    document.getElementById("modal-ocorrencia-data-resolucao").textContent = ocorrencia.dataResolucao || "Não definida";
    document.getElementById("modal-ocorrencia-data-acordada").textContent = ocorrencia.dataAcordada || "Não definida";

    // Descrição e solução
    document.getElementById("modal-ocorrencia-descricao").textContent = ocorrencia.ocorrencia;
    document.getElementById("modal-ocorrencia-detalhamento").textContent = ocorrencia.ocorrenciaDetalhada;
    document.getElementById("modal-ocorrencia-solucao-imediata").textContent = ocorrencia.solucaoImediata;
    document.getElementById("modal-ocorrencia-sugestao-solucao").textContent = ocorrencia.sugestaoSolucaoDefinitiva;

    // Tratamento
    const tratamentoElement = document.getElementById("modal-ocorrencia-tratamento");
    if (ocorrencia.statusOcorrencia.descricao === "Finalizada") {
        // Exibir o valor do banco para status finalizados
        tratamentoElement.textContent = ocorrencia.tratamentoOcorrencia || "Tratamento não definido";
    } else {
        // Exibir o prazo processado para outros status
        tratamentoElement.textContent = ocorrencia.tratamentoOcorrencia || "Sem prazo definido";
    }

  //teste

    // Status com estilo do card
    const statusElement = document.getElementById("modal-ocorrencia-status");
    statusElement.textContent = ocorrencia.statusOcorrencia.descricao;
    statusElement.className = ""; // Resetar classes anteriores
    switch (ocorrencia.statusOcorrencia.descricao) {
        case "Recebida":
            statusElement.classList.add("status-recebida");
            break;
        case "Em tratamento":
            statusElement.classList.add("status-em-tratamento");
            break;
        case "Finalizada":
            statusElement.classList.add("status-finalizada");
            break;
        default:
            statusElement.classList.add("status-default");
    }

    // Evidência
    const evidenciaLink = document.getElementById("modal-ocorrencia-evidencia");
    if (ocorrencia.evidencia && ocorrencia.statusOcorrencia.descricao === "Finalizada") {
        evidenciaLink.href = ocorrencia.evidencia;
        evidenciaLink.textContent = "Ver Evidência";
        evidenciaLink.style.display = "inline";
    } else {
        evidenciaLink.style.display = "none";
    }

    // Exibir modal
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
