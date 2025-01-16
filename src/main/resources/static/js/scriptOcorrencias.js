// Variável para armazenar a ocorrência atual
let ocorrenciaAtual = null;

let userRoles = [];

// Função para carregar roles do usuário
async function carregarRolesUsuario() {
    try {
        const response = await fetch("/api/user/info");
        if (response.ok) {
            const data = await response.json();
            userRoles = data.roles; // Armazena as roles
        } else {
            console.error("Erro ao obter informações do usuário.");
        }
    } catch (error) {
        console.error("Erro ao carregar roles do usuário:", error);
    }
}

// Chamar ao carregar a página
document.addEventListener("DOMContentLoaded", carregarRolesUsuario);


// Função para carregar roles do usuário
async function carregarRolesUsuario() {
    try {
        const response = await fetch("/api/user/info");
        if (response.ok) {
            const data = await response.json();
            userRoles = data.roles; // Armazena as roles
        } else {
            console.error("Erro ao obter informações do usuário.");
        }
    } catch (error) {
        console.error("Erro ao carregar roles do usuário:", error);
    }
}

// Chamar ao carregar a página
document.addEventListener("DOMContentLoaded", carregarRolesUsuario);


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

        let ocorrencias = id ? [await response.json()] : await response.json();

        // Aplicar o filtro de vencidas, se estiver ativo
        if (filtroVencidasAtivo) {
            const hoje = new Date();
            ocorrencias = ocorrencias.filter(ocorrencia => {
                if (ocorrencia.dataAcordada) {
                    const dataAcordada = new Date(ocorrencia.dataAcordada);
                    return (
                        dataAcordada < hoje &&
                        ocorrencia.statusOcorrencia.descricao !== "Finalizada"
                    );
                }
                return false;
            });
        }

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
            gravidadeElement.style.color = "orange";
            break;
        case "Inconformidade Média":
            gravidadeElement.style.color = "lightcoral";
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

let filtroVencidasAtivo = false; // Controla se o filtro de vencidas está ativo

// Filtrar ocorrências vencidas
async function filtrarOcorrenciasVencidas() {
    filtroVencidasAtivo = true; // Ativar o filtro de vencidas
    await carregarOcorrencias(); // Recarregar ocorrências com o filtro ativo
}

function resetarFiltroVencidas() {
    // Resetar o filtro de ocorrências vencidas
    filtroVencidasAtivo = false;

    // Limpar valores dos filtros
    document.getElementById("filtro-obra").value = "";
    document.getElementById("filtro-grupo").value = "";
    document.getElementById("filtro-status").value = "";
    document.getElementById("filtro-data").value = "";
    document.getElementById("filtro-id").value = "";

    // Recarregar todas as ocorrências sem filtros
    carregarOcorrencias();
}



// Filtrar não conformidades
async function filtrarNaoConformidades() {
    try {
        const response = await fetch('/api/ocorrencias/filtros'); // Ajuste o endpoint se necessário
        const ocorrencias = await response.json();

        const naoConformidades = ocorrencias.filter(ocorrencia => {
            return ["Inconformidade Leve", "Inconformidade Média", "Inconformidade Grave"].includes(
                ocorrencia.gravidade.gravidade
            );
        });

        // Ordenar da mais grave para a mais leve
        const gravidadeMap = {
            "Inconformidade Grave": 3,
            "Inconformidade Média": 2,
            "Inconformidade Leve": 1,
        };

        naoConformidades.sort((a, b) => gravidadeMap[b.gravidade.gravidade] - gravidadeMap[a.gravidade.gravidade]);

        // Atualizar badge
        document.getElementById("badge-nao-conformidades").textContent = naoConformidades.length;

        // Exibir ocorrências de não conformidades
        exibirOcorrencias(naoConformidades);
    } catch (error) {
        console.error("Erro ao filtrar não conformidades:", error);
    }
}

// Atualizar os badges ao carregar a página
async function atualizarBadges() {
    try {
        const response = await fetch('/api/ocorrencias/filtros'); // Ajuste o endpoint se necessário
        const ocorrencias = await response.json();

        const vencidas = ocorrencias.filter(ocorrencia => {
            if (ocorrencia.dataAcordada) {
                const hoje = new Date();
                const dataAcordada = new Date(ocorrencia.dataAcordada);
                return dataAcordada < hoje && ocorrencia.statusOcorrencia.descricao !== "Finalizada";
            }
            return false;
        });

        const naoConformidades = ocorrencias.filter(ocorrencia => {
            return ["Inconformidade Leve", "Inconformidade Média", "Inconformidade Grave"].includes(
                ocorrencia.gravidade.gravidade
            );
        });

        document.getElementById("badge-vencidas").textContent = vencidas.length;
        document.getElementById("badge-nao-conformidades").textContent = naoConformidades.length;
    } catch (error) {
        console.error("Erro ao atualizar badges:", error);
    }
}

// Chamar a função ao carregar a página
document.addEventListener("DOMContentLoaded", atualizarBadges);

function abrirModalEditar() {
    if (!ocorrenciaAtual) {
        console.error("A ocorrência atual não foi definida.");
        return;
    }

    // Verificar se o usuário tem permissão de ADMIN
    if (!userRoles.includes("ROLE_ADMIN")) {
        alert("Você não tem permissão para editar esta ocorrência.");
        return;
    }

    // Preencher os campos de edição
    document.getElementById("gravidade-editar").value = ocorrenciaAtual.gravidade.gravidadeId || "";
    document.getElementById("status-editar").value = ocorrenciaAtual.statusOcorrencia.id || "";
    document.getElementById("data-acordada-editar").value = ocorrenciaAtual.dataAcordada || "";
    document.getElementById("tratamento-editar").value = ocorrenciaAtual.tratamentoOcorrencia || "";
    document.getElementById("data-resolucao-editar").value = ocorrenciaAtual.dataResolucao || "";
    document.getElementById("evidencia-editar").value = ocorrenciaAtual.evidencia || "";

    // Exibir o modal de edição
    document.getElementById("modal-editar").style.display = "flex";
}



function fecharModalEditar() {
    document.getElementById("modal-editar").style.display = "none";
}

async function salvarEdicao() {
    const id = ocorrenciaAtual.ocorrenciaId;

    const gravidadeId = document.getElementById("gravidade-editar").value;
    const statusId = document.getElementById("status-editar").value;
    const dataAcordada = document.getElementById("data-acordada-editar").value;
    const tratamento = document.getElementById("tratamento-editar").value;
    const dataResolucao = document.getElementById("data-resolucao-editar").value;
    const evidencia = document.getElementById("evidencia-editar").value;

    const ocorrenciaEditada = {
        gravidade: { gravidadeId },
        statusOcorrencia: { id: statusId },
        dataAcordada,
        tratamentoOcorrencia: tratamento,
        dataResolucao,
        evidencia
    };

    try {
        const response = await fetch(`/api/ocorrencias/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(ocorrenciaEditada)
        });

        if (response.ok) {
            alert("Ocorrência atualizada com sucesso!");
            fecharModalEditar();
            carregarOcorrencias();
        } else {
            alert("Erro ao atualizar ocorrência.");
        }
    } catch (error) {
        console.error("Erro ao salvar edições:", error);
    }
}


async function carregarCamposEdicao() {
    // Carregar opções de status
    const status = await fetch("/api/status-ocorrencia").then(res => res.json());
    const statusEditar = document.getElementById("status-editar");
    statusEditar.innerHTML = ""; // Limpar opções existentes
    status.forEach(st => {
        const option = document.createElement("option");
        option.value = st.id;
        option.textContent = st.descricao;
        statusEditar.appendChild(option);
    });
}
async function carregarGravidades() {
    try {
        const response = await fetch("/api/gravidade-ocorrencias");
        const gravidades = await response.json();
        const gravidadeEditar = document.getElementById("gravidade-editar");

        gravidadeEditar.innerHTML = ""; // Limpar opções existentes

        gravidades.forEach(gravidade => {
            const option = document.createElement("option");
            option.value = gravidade.gravidadeId;
            option.textContent = gravidade.gravidade;
            gravidadeEditar.appendChild(option);
        });
    } catch (error) {
        console.error("Erro ao carregar gravidades:", error);
    }
}



// Chamar ao carregar a página
document.addEventListener("DOMContentLoaded", carregarCamposEdicao);
document.addEventListener("DOMContentLoaded", carregarGravidades);

async function baixarRelatorio() {
    const obraId = document.getElementById("filtro-obra").value;
    const statusId = document.getElementById("filtro-status").value;
    const grupoId = document.getElementById("filtro-grupo").value;
    const data = document.getElementById("filtro-data").value;

    const params = new URLSearchParams();
    if (obraId) params.append("obraId", obraId);
    if (statusId) params.append("statusId", statusId);
    if (grupoId) params.append("grupoId", grupoId);
    if (data) params.append("data", data);

    try {
        const response = await fetch(`/api/ocorrencias/filtros?${params.toString()}`);
        const ocorrencias = await response.json();

        if (ocorrencias.length === 0) {
            alert("Nenhuma ocorrência encontrada para os filtros selecionados.");
            return;
        }

        const { jsPDF } = window.jspdf;
        const doc = new jsPDF();

        const margemEsquerda = 10;
        let linha = 20;

        // Cabeçalho do relatório
        doc.setFontSize(16);
        doc.text("Relatório de Ocorrências", margemEsquerda, linha);
        doc.setFontSize(12);
        linha += 40; // Espaçamento maior no cabeçalho
        doc.text(`Data de Geração: ${new Date().toLocaleDateString()}`, margemEsquerda, linha);
        linha += 40; // Espaçamento maior antes da tabela

        // Tabela principal de dados
        const colunas = ["Local", "Grupo", "Gravidade", "Supervisor", "Data Registro"];
        const dadosTabela = ocorrencias.map(ocorrencia => [
            ocorrencia.obra.nome,
            ocorrencia.grupoOcorrencia.grupoOcorrencia,
            ocorrencia.gravidade.gravidade,
            ocorrencia.supervisor.nome,
            ocorrencia.dataRegistro,
        ]);

        doc.autoTable({
            head: [colunas],
            body: dadosTabela,
            startY: linha,
            margin: { left: margemEsquerda, right: margemEsquerda },
            styles: { fontSize: 10, cellPadding: 8 }, // Espaçamento nas células
            headStyles: { fillColor: [52, 152, 219], textColor: 255 },
            alternateRowStyles: { fillColor: [245, 245, 245] },
        });

        // Posição após a tabela
        linha = doc.autoTable.previous.finalY + 40; // Espaçamento maior antes das descrições

        // Adicionar descrições detalhadas
        ocorrencias.forEach((ocorrencia, index) => {
            const descricao = ocorrencia.ocorrenciaDetalhada || "Não definida";
            const dataAcordada = ocorrencia.dataAcordada || "Não definida";
            const atraso = calcularAtraso(ocorrencia.dataAcordada, ocorrencia.statusOcorrencia.descricao) || "Sem atraso";

            // Adicionar linha horizontal para separar as descrições
            doc.setDrawColor(0); // Preto
            doc.setLineWidth(0.5);
            doc.line(margemEsquerda, linha - 10, 200, linha - 10); // Linha horizontal completa

            // Nova seção para cada ocorrência
            doc.setFontSize(12);
            doc.setFont("bold");
            doc.text(`Descrição Detalhada (${index + 1}):`, margemEsquerda, linha);
            linha += 20; // Espaçamento maior antes do texto da descrição

            doc.setFontSize(10);
            doc.setFont("normal");
            doc.text(`Descrição:`, margemEsquerda, linha);
            linha += 10; // Espaçamento entre título e texto

            // Adicionar descrição e calcular altura necessária
            const larguraTexto = 190; // Largura máxima permitida para o texto
            const alturaTexto = doc.getTextDimensions(descricao, { maxWidth: larguraTexto }).h;
            doc.text(descricao, margemEsquerda, linha, { maxWidth: larguraTexto });
            linha += alturaTexto + 20; // Adicionar altura da descrição e um espaçamento extra

            doc.text(`Data Acordada: ${dataAcordada}`, margemEsquerda, linha);
            linha += 20; // Espaçamento após a data

            // Texto do atraso em vermelho
            doc.setTextColor(255, 0, 0); // Cor vermelha
            doc.text(`Atraso: ${atraso}`, margemEsquerda, linha);
            doc.setTextColor(0, 0, 0); // Retornar para cor preta padrão
            linha += 50; // Espaçamento maior antes do próximo registro

            // Adicionar nova página se necessário
            if (linha > 260) {
                doc.addPage();
                linha = 20;
            }
        });

        // Salvar o PDF
        doc.save("relatorio-ocorrencias.pdf");
    } catch (error) {
        console.error("Erro ao gerar relatório:", error);
        alert("Erro ao gerar relatório. Tente novamente mais tarde.");
    }
}

function calcularAtraso(dataAcordada, status) {
    if (!dataAcordada || status === "Finalizada") return null;

    const hoje = new Date();
    const data = new Date(dataAcordada);

    if (data < hoje) {
        const diasAtrasados = Math.ceil((hoje - data) / (1000 * 60 * 60 * 24));
        return `${diasAtrasados} dias atrasados`;
    }

    return null;
}
