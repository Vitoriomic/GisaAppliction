// Função para carregar opções dinâmicas para os filtros

let condicionanteAtual = null; // Armazena o condicionante atual para edição

async function carregarOpcoes() {
    try {
        // Carregar obras
        const obrasResponse = await fetch("/api/obras");
        const obras = await obrasResponse.json();
        const filtroObra = document.getElementById("filtro-obra");
        obras.forEach(obra => {
            const option = document.createElement("option");
            option.value = obra.obraid; // O id será enviado no filtro
            option.textContent = obra.nome; // Nome será exibido
            filtroObra.appendChild(option);
        });

        // Carregar status
        const statusResponse = await fetch("/api/status-condicionantes");
        const statusList = await statusResponse.json();
        const filtroStatus = document.getElementById("filtro-status");
        statusList.forEach(status => {
            const option = document.createElement("option");
            option.value = status.statusId; // O id será enviado no filtro
            option.textContent = status.status; // Nome será exibido
            filtroStatus.appendChild(option);
        });
    } catch (error) {
        console.error("Erro ao carregar opções:", error);
    }
}

// Função para carregar condicionantes com filtros aplicados
async function carregarCondicionantes() {
    const spinner = document.getElementById("loading-spinner");
    const button = document.getElementById("btn-filtrar");

    try {
        // Exibir o círculo de carregamento
        spinner.style.display = "inline-block";
        button.disabled = true; // Desativar o botão durante o carregamento

        const obraId = document.getElementById("filtro-obra").value; // ID da obra selecionada
        const statusId = document.getElementById("filtro-status").value; // ID do status selecionado
        const id = document.getElementById("filtro-id").value.trim(); // ID da condicionante

        let url;
        if (id) {
            // Caso um ID tenha sido fornecido, buscar diretamente pelo ID
            url = `/api/condicionantes/${id}`;
        } else {
            // Construir parâmetros de busca para obra e status
            const params = new URLSearchParams();
            if (obraId) params.append("obraId", obraId);
            if (statusId) params.append("statusId", statusId);

            url = `/api/condicionantes?${params.toString()}`;
        }

        // Chamar a API
        const response = await fetch(url);

        if (!response.ok) {
            throw new Error("Erro ao buscar condicionantes.");
        }

        const condicionantes = id ? [await response.json()] : await response.json(); // Se for busca por ID, retornará um único objeto
        exibirCondicionantes(condicionantes);
    } catch (error) {
        console.error("Erro ao carregar condicionantes:", error);
    } finally {
        // Ocultar o círculo de carregamento e reativar o botão
        spinner.style.display = "none";
        button.disabled = false;
    }
}



// Exibir os condicionantes nos cards
function exibirCondicionantes(condicionantes) {
    const container = document.getElementById("cards-container");
        const totalCondicionantes = document.getElementById("total-condicionantes");

        // Atualiza o número total de condicionantes encontrados
        totalCondicionantes.textContent = `(${condicionantes.length})`;

        // Limpar o container
        container.innerHTML = "";

    if (condicionantes.length === 0) {
        container.innerHTML = "<p>Nenhum condicionante encontrado.</p>";
    } else {
        condicionantes.forEach(condicionante => {
            const card = document.createElement("div");
            card.className = "card";
            card.innerHTML = `
                <p><strong>ID:</strong> ${condicionante.condicionanteId}</p>
                <p><strong>Obra:</strong> ${condicionante.obra.nome}</p>
                <p><strong>Status:</strong> ${condicionante.statusCondicionante.status}</p>
                <p><strong>Identificação:</strong> ${condicionante.identificacao}</p>
            `;
            card.addEventListener("click", () => abrirModal(condicionante));
            container.appendChild(card);
        });
    }
}

// Exibir detalhes no modal
function abrirModal(condicionante) {
    condicionanteAtual = condicionante; // Armazena o condicionante atual

    document.getElementById("modal-condicionante-id").textContent = condicionante.condicionanteId;
    document.getElementById("modal-condicionante-obra").textContent = condicionante.obra.nome;
    document.getElementById("modal-condicionante-descricao").textContent = condicionante.condicionante;
    document.getElementById("modal-condicionante-identificacao").textContent = condicionante.identificacao;
    document.getElementById("modal-condicionante-status").textContent = condicionante.statusCondicionante.status;
    document.getElementById("modal-condicionante-comprovacao").textContent = condicionante?.comprovacao;

    const comprovacaoElement = document.getElementById("modal-condicionante-comprovacao");
    if (condicionante.comprovacao && condicionante.comprovacao.startsWith("http")) {
        comprovacaoElement.textContent = "Comprovação";
        comprovacaoElement.href = condicionante.comprovacao;
        comprovacaoElement.target = "_blank";
        comprovacaoElement.style.display = "inline";
    } else {
        comprovacaoElement.textContent = "Nenhuma comprovação disponível";
        comprovacaoElement.href = "#";
        comprovacaoElement.style.display = "none";
    }

    document.getElementById("modal-condicionante-prazo").textContent = condicionante.prazoVencimento;
    document.getElementById("modal-condicionante-acao").textContent = condicionante.acaoAtendimento;
    document.getElementById("modal-condicionante-situacao").textContent = condicionante.situacao;
    document.getElementById("modal-condicionante-protocolo").textContent = condicionante.protocolacao.status;
    document.getElementById("modal-condicionante-resp-terceiros").textContent = condicionante.responsabilidadeTerceiros.responsabilidade;
    document.getElementById("modal-condicionante-resp-cliente").textContent = condicionante.responsabilidadeCliente.responsabilidade;
    document.getElementById("modal-condicionante-resp-zago").textContent = condicionante.responsabilidadeZago.responsabilidade;
    document.getElementById("modal-condicionante-data").textContent = condicionante.dataAtendimento;

    document.getElementById("modal-condicionante").style.display = "flex";
}


// Fechar modal
function fecharModal() {
    document.getElementById("modal-condicionante").style.display = "none";
}

// Abrir modal de adicionar
function abrirModalAdicionar() {
    document.getElementById("modal-adicionar").style.display = "flex";
    carregarOpcoesAdicionar();
}

// Fechar modal de adicionar
function fecharModalAdicionar() {
    document.getElementById("modal-adicionar").style.display = "none";
}

// Carregar opções para o modal de adicionar
async function carregarOpcoesAdicionar() {
    // Carregar obras
    const obrasResponseAd = await fetch("/api/obras");
    const obras = await obrasResponseAd.json();
    const obraSelect = document.getElementById("obra-adicionar");
    obraSelect.innerHTML = '<option value="">Selecione uma obra</option>';
    obras.forEach(obra => {
        const option = document.createElement("option");
        option.value = obra.obraid;
        option.textContent = obra.nome;
        obraSelect.appendChild(option);
    });

    // Carregar status
    const statusResponseAd = await fetch("/api/status-condicionantes");
    const statusList = await statusResponseAd.json();
    const statusSelect = document.getElementById("status-adicionar");
    statusSelect.innerHTML = '<option value="">Selecione um status</option>';
    statusList.forEach(status => {
        const option = document.createElement("option");
        option.value = status.statusid;
        option.textContent = status.status;
        statusSelect.appendChild(option);
    });

    // Carregar responsabilidades
    await carregarResponsabilidades("/api/responsabilidades/terceiros", "responsavel-terceiros-adicionar");
    await carregarResponsabilidades("/api/responsabilidades/cliente", "responsavel-cliente-adicionar");
    await carregarResponsabilidades("/api/responsabilidades/zago", "responsavel-zago-adicionar");
}

async function carregarResponsabilidades(apiUrl, selectId) {
    const response = await fetch(apiUrl);
    const responsabilidades = await response.json();
    const select = document.getElementById(selectId);
    select.innerHTML = '<option value="">Selecione</option>';
    responsabilidades.forEach(resp => {
        const option = document.createElement("option");
        option.value = resp.id;
        option.textContent = resp.responsabilidade;
        select.appendChild(option);
    });
}


// Adicionar condicionante
async function adicionarCondicionante() {
    const obraId = document.getElementById("obra-adicionar").value;
    const statusId = document.getElementById("status-adicionar").value;
    const identificacao = document.getElementById("identificacao-adicionar").value;
    const descricao = document.getElementById("condicionante-adicionar").value;
    const comprovacao = document.getElementById("comprovacao-adicionar").value;
    const prazo = document.getElementById("prazo-adicionar").value;
    const situacao = document.getElementById("situacao-adicionar").value;
    const protocolo = document.getElementById("protocolo-adicionar").value;
    const respTerceiros = document.getElementById("responsavel-terceiros-adicionar").value;
    const respCliente = document.getElementById("responsavel-cliente-adicionar").value;
    const respZago = document.getElementById("responsavel-zago-adicionar").value;
    const dataAtendimento = document.getElementById("data-atendimento-adicionar").value;

    const data = {
        obraId,
        statusId,
        identificacao,
        descricao,
        comprovacao,
        prazoVencimento: prazo,
        situacao,
        protocolo,
        respTerceirosId: respTerceiros,
        respClienteId: respCliente,
        respZagoId: respZago,
        dataAtendimento,
    };

    try {
        const response = await fetch("/api/condicionantes", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data),
        });

        if (response.ok) {
            const result = await response.json();
            alert(`Condicionante adicionada com sucesso! ID: ${result.id}`);
            fecharModalAdicionar();
        } else {
            const error = await response.text();
            alert(`Erro ao adicionar: ${error}`);
        }
    } catch (err) {
        alert(`Erro: ${err.message}`);
    }
}

// Abrir modal de edição e preencher dados
async function abrirModalEditar() {
    console.log("Abrindo modal de edição...");
    if (!condicionanteAtual) {
        console.error("Nenhum condicionante selecionado para edição.");
        return;
    }

    try {
        // Fechar a modal de detalhes, se estiver aberta
        fecharModal();

        console.log("Condicionante atual:", condicionanteAtual);

        // Preencher os campos do formulário de edição
        document.getElementById("condicionante-editar").value = condicionanteAtual.condicionante || "";
        document.getElementById("identificacao-editar").value = condicionanteAtual.identificacao || "";
        document.getElementById("comprovacao-editar").value = condicionanteAtual.comprovacao || "";
        document.getElementById("prazo-editar").value = condicionanteAtual.prazoVencimento || "";
        document.getElementById("situacao-editar").value = condicionanteAtual.situacao || "";
        document.getElementById("acao-editar").value = condicionanteAtual.acaoAtendimento || "";
        document.getElementById("protocolo-editar").value = condicionanteAtual.protocolacao?.status || "";
        document.getElementById("data-atendimento-editar").value = condicionanteAtual.dataAtendimento || "";

        // Carregar selects e selecionar as opções corretas
        await carregarSelectComValor("/api/obras", "obra-editar", condicionanteAtual.obra?.obraid);
        await carregarSelectComValor("/api/status-condicionantes", "status-editar", condicionanteAtual.statusCondicionante?.statusId);
        await carregarSelectComValor("/api/responsabilidades/terceiros", "responsavel-terceiros-editar", condicionanteAtual.responsabilidadeTerceiros?.respterceirosid);
        await carregarSelectComValor("/api/responsabilidades/cliente", "responsavel-cliente-editar", condicionanteAtual.responsabilidadeCliente?.respclienteid);
        await carregarSelectComValor("/api/responsabilidades/zago", "responsavel-zago-editar", condicionanteAtual.responsabilidadeZago?.respzagoid);

        console.log("Modal de edição pronta para exibir.");

        // Exibir a modal de edição
        const modal = document.getElementById("modal-editar");
        if (modal) {
            modal.style.display = "flex";
        } else {
            console.error("Modal de edição não encontrada.");
        }
    } catch (error) {
        console.error("Erro ao abrir modal de edição:", error);
    }
}


// Carregar opções para um select e selecionar o valor atual
async function carregarSelectComValor(apiUrl, selectId, valorAtual) {
    try {
        console.log(`Carregando opções para ${selectId}...`);
        const response = await fetch(apiUrl);
        if (!response.ok) {
            throw new Error(`Erro ao buscar dados de ${selectId}`);
        }

        const dados = await response.json();
        console.log(`Dados recebidos para ${selectId}:`, dados);

        const select = document.getElementById(selectId);
        if (!select) {
            console.error(`Select com ID ${selectId} não encontrado.`);
            return;
        }

        select.innerHTML = '<option value="">Selecione</option>'; // Limpar opções anteriores

        // Adicionar opções ao select
        dados.forEach(dado => {
            const option = document.createElement("option");
            option.value = dado.obraid || dado.statusId || dado.respterceirosid || dado.respclienteid || dado.respzagoid; // Ajuste conforme a API
            option.textContent = dado.nome || dado.status || dado.responsabilidade; // Ajuste conforme a API
            if (option.value == valorAtual) {
                option.selected = true; // Selecionar o valor atual
            }
            select.appendChild(option);
        });

        console.log(`Select ${selectId} carregado com sucesso.`);
    } catch (error) {
        console.error(`Erro ao carregar opções para ${selectId}:`, error);
    }
}


// Fechar a modal de edição
function fecharModalEditar() {
    document.getElementById("modal-editar").style.display = "none";
}


// Salvar alterações na condicionante
async function salvarCondicionante() {
    const condicionanteAtualizada = {
        obraId: document.getElementById("obra-editar").value,
        identificacao: document.getElementById("identificacao-editar").value,
        condicionante: document.getElementById("condicionante-editar").value,
        statusId: document.getElementById("status-editar").value,
        comprovacao: document.getElementById("comprovacao-editar").value,
        prazoVencimento: document.getElementById("prazo-editar").value,
        situacao: document.getElementById("situacao-editar").value,
        protocolo: document.getElementById("protocolo-editar").value,
        respTerceirosId: document.getElementById("responsavel-terceiros-editar").value,
        respClienteId: document.getElementById("responsavel-cliente-editar").value,
        respZagoId: document.getElementById("responsavel-zago-editar").value,
        dataAtendimento: document.getElementById("data-atendimento-editar").value,
    };

    try {
        const response = await fetch(`/api/condicionantes/${currentCondicionanteId}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(condicionanteAtualizada),
        });

        if (response.ok) {
            alert("Condicionante atualizada com sucesso!");
            fecharModalEditar();
            carregarCondicionantes(); // Atualiza a lista
        } else {
            const error = await response.text();
            alert(`Erro ao atualizar: ${error}`);
        }
    } catch (err) {
        alert(`Erro: ${err.message}`);
    }
}


// Capturar dados do formulário
function capturarDadosFormulario() {
    return {
        obra: { obraid: document.getElementById("obra-adicionar").value },
        condicionante: document.getElementById("condicionante-adicionar").value,
        identificacao: document.getElementById("identificacao-adicionar").value,
        statusCondicionante: { statusid: document.getElementById("status-adicionar").value },
        comprovacao: document.getElementById("comprovacao-adicionar").value,
        prazovencimento: document.getElementById("prazo-adicionar").value,
        acaoatendimento: document.getElementById("acao-adicionar").value,
        situacao: document.getElementById("situacao-adicionar").value,
        protocolacao: document.getElementById("protocolo-adicionar").value,
        respTerceiros: { respterceirosid: document.getElementById("responsavel-terceiros-adicionar").value },
        respCliente: { respclienteid: document.getElementById("responsavel-cliente-adicionar").value },
        respZago: { respzagoid: document.getElementById("responsavel-zago-adicionar").value },
        dataatendimento: document.getElementById("data-atendimento-adicionar").value,
    };
}

// Fechar modal
function fecharModalAdicionar() {
    document.getElementById("modal-adicionar").style.display = "none";
}




// Inicializar a página
document.addEventListener("DOMContentLoaded", () => {
    carregarOpcoes();
    carregarCondicionantes();
});
