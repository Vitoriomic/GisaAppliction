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
        option.value = status.statusId;
        option.textContent = status.status;
        statusSelect.appendChild(option);
    });

    // Carregar protocolações
    const protocolacoesResponse = await fetch("/api/protocolacoes");
        const protocolacoes = await protocolacoesResponse.json();
        const protocolacaoSelect = document.getElementById("protocolo-adicionar");
        protocolacaoSelect.innerHTML = '<option value="">Selecione uma protocolação</option>';
        protocolacoes.forEach(protocolacao => {
            const option = document.createElement("option");
            option.value = protocolacao.protocolacaoId;
            option.textContent = protocolacao.status; // Ajustar conforme o campo da entidade Protocolacao
            protocolacaoSelect.appendChild(option);
        });

    // Carregar Responsabilidade de Terceiros
    const respTerceirosResponse = await fetch("/api/responsabilidades/terceiros");
            const respTerceiros = await respTerceirosResponse.json();
            const  respTerceirosSelect = document.getElementById("responsavel-terceiros-adicionar");
            respTerceirosSelect.innerHTML = '<option value="">Selecione uma responsbilidade</option>';
            respTerceiros.forEach(respTerceiros => {
                const option = document.createElement("option");
                option.value = respTerceiros.respterceirosid;
                option.textContent = respTerceiros.responsabilidade; // Ajustar conforme o campo da entidade Protocolacao
                respTerceirosSelect.appendChild(option);
            });

    // Carregar Responsabilidade do Cliente
    const respClienteResponse = await fetch("/api/responsabilidades/cliente");
            const respCliente = await respClienteResponse.json();
            const  respClienteSelect = document.getElementById("responsavel-cliente-adicionar");
            respClienteSelect.innerHTML = '<option value="">Selecione uma responsbilidade</option>';
            respCliente.forEach(respCliente => {
                const option = document.createElement("option");
                option.value = respCliente.respclienteid;
                option.textContent = respCliente.responsabilidade; // Ajustar conforme o campo da entidade Protocolacao
                respClienteSelect.appendChild(option);
            });

    // Carregar Responsabilidade da Zago
    const respZagoResponse = await fetch("/api/responsabilidades/zago");
            const respZago = await respZagoResponse.json();
            const  respZagoSelect = document.getElementById("responsavel-zago-adicionar");
            respZagoSelect.innerHTML = '<option value="">Selecione uma responsbilidade</option>';
            respZago.forEach(respZago => {
                const option = document.createElement("option");
                option.value = respZago.respzagoid;
                option.textContent = respZago.responsabilidade; // Ajustar conforme o campo da entidade Protocolacao
                respZagoSelect.appendChild(option);
            });


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

const obraId = document.getElementById("obra-adicionar").value || null;
    const statusId = document.getElementById("status-adicionar").value || null;
    const identificacao = document.getElementById("identificacao-adicionar").value || null;
    const condicionante = document.getElementById("condicionante-adicionar").value || null;
    const comprovacao = document.getElementById("comprovacao-adicionar").value || null;
    const prazo = document.getElementById("prazo-adicionar").value || null;
    const acaoAtendimento = document.getElementById("acao-adicionar").value || null;
    const situacao = document.getElementById("situacao-adicionar").value || null;
    const protocolacaoId = document.getElementById("protocolo-adicionar").value || null;
    const respTerceiros = document.getElementById("responsavel-terceiros-adicionar").value || null;
    const respCliente = document.getElementById("responsavel-cliente-adicionar").value || null;
    const respZago = document.getElementById("responsavel-zago-adicionar").value || null;
    const dataAtendimento = document.getElementById("data-atendimento-adicionar").value || null;

const data = {
    obraId: obraId ? Number(obraId) : null,
    condicionante: condicionante || null,
    identificacao: identificacao || null,
    statusId: statusId ? Number(statusId) : null,
    comprovacao: comprovacao || null,
    prazoVencimento: prazo || null,
    acaoAtendimento: acaoAtendimento || null,
    situacao: situacao || null,
    protocolacaoId: protocolacaoId ? Number(protocolacaoId) : null,
    respTerceirosId: respTerceiros ? Number(respTerceiros) : null,
    respClienteId: respCliente ? Number(respCliente) : null,
    respZagoId: respZago ? Number(respZago) : null,
    dataAtendimento: dataAtendimento || null,
};

    try {
        const response = await fetch("/api/condicionantes", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data),
        });

        if (response.ok) {
            const result = await response.json();
            alert(`Condicionante adicionada com sucesso! ID: ${result.condicionanteId}`);
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
        document.getElementById("data-atendimento-editar").value = condicionanteAtual.dataAtendimento || "";

        // Carregar selects e selecionar as opções corretas
        await carregarSelectComValor("/api/obras", "obra-editar", condicionanteAtual.obra?.obraid);
        await carregarSelectComValor("/api/status-condicionantes", "status-editar", condicionanteAtual.statusCondicionante?.statusId);
        await carregarSelectComValor("/api/responsabilidades/terceiros", "responsavel-terceiros-editar", condicionanteAtual.responsabilidadeTerceiros?.respterceirosid);
        await carregarSelectComValor("/api/responsabilidades/cliente", "responsavel-cliente-editar", condicionanteAtual.responsabilidadeCliente?.respclienteid);
        await carregarSelectComValor("/api/responsabilidades/zago", "responsavel-zago-editar", condicionanteAtual.responsabilidadeZago?.respzagoid);
        await carregarSelectComValor("/api/protocolacoes", "protocolo-editar", condicionanteAtual.protocolacao?.protocolacaoId);


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
            option.value = dado.obraid || dado.statusId || dado.respterceirosid || dado.respclienteid || dado.respzagoid || dado.protocolacaoId; // Ajuste conforme a API
            option.textContent = dado.nome || dado.status || dado.responsabilidade || dado.status; // Ajuste conforme a API
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
            obraId: document.getElementById("obra-editar").value || null,
            identificacao: document.getElementById("identificacao-editar").value || null,
            condicionante: document.getElementById("condicionante-editar").value || null,
            statusId: document.getElementById("status-editar").value || null,
            comprovacao: document.getElementById("comprovacao-editar").value || null,
            prazoVencimento: document.getElementById("prazo-editar").value || null,
            situacao: document.getElementById("situacao-editar").value || null,
            protocolacaoId: document.getElementById("protocolo-editar").value || null,
            respTerceirosId: document.getElementById("responsavel-terceiros-editar").value || null,
            respClienteId: document.getElementById("responsavel-cliente-editar").value || null,
            respZagoId: document.getElementById("responsavel-zago-editar").value || null,
            dataAtendimento: document.getElementById("data-atendimento-editar").value || null,
        };

    try {
        const response = await fetch(`/api/condicionantes/${condicionanteAtual.condicionanteId}`, {
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

// Fechar modal
function fecharModalAdicionar() {
    document.getElementById("modal-adicionar").style.display = "none";
}

async function excluirCondicionante(id) {
    if (!confirm("Tem certeza de que deseja excluir esta condicionante?")) {
        return;
    }

    try {
        const response = await fetch(`/api/condicionantes/${id}`, {
            method: "DELETE",
        });

        if (response.ok) {
            alert("Condicionante excluída com sucesso!");
            carregarCondicionantes(); // Atualiza a lista de condicionantes
        } else {
            const errorText = await response.text();
            alert(`Erro ao excluir: ${errorText}`);
        }
    } catch (err) {
        console.error("Erro ao excluir a condicionante:", err);
        alert(`Erro ao excluir a condicionante: ${err.message}`);
    }
}


// Inicializar a página
document.addEventListener("DOMContentLoaded", () => {
    carregarOpcoes();
    carregarCondicionantes();
});
