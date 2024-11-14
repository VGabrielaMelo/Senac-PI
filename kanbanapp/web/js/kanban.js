const modal = document.getElementById('modal');
const taskForm = document.getElementById('taskForm');  // Pegando o formulário
const descriptionInput = document.getElementById('description');
const priorityInput = document.getElementById('priority');
const deadlineInput = document.getElementById('deadLine');
const column = document.getElementById('column');
const idInput = document.getElementById('idInput');

const creationModeTitle = document.getElementById('creationModeTitle');
const editingModeTitle = document.getElementById('editingModeTitle');

const creationModeButton = document.getElementById('creationModeButton');
const editingModeButton = document.getElementById('editingModeButton');

function openModal(data_column) {
    modal.style.display = 'flex';

    // Limpar os campos antes de abrir a modal
    document.getElementById('idInput').value = '';  // Limpar ID
    document.getElementById('description').value = '';  // Limpar descrição
    document.getElementById('priority').value = 'Baixa';  // Limpar prioridade
    document.getElementById('deadLine').value = '';  // Limpar prazo
    column.value = data_column;  // Definir a coluna selecionada

    // Desabilitar o select de coluna no modo de criação

    // Ajustar o action para quadroSubmit.jsp (criação de nova tarefa)
    taskForm.action = 'quadroSubmit.jsp';

    // Mostrar elementos do modo de criação
    creationModeTitle.style.display = "block";
    creationModeButton.style.display = "block";

    // Esconder elementos do modo de edição
    editingModeTitle.style.display = "none";
    editingModeButton.style.display = "none";
}

function openEditModal(id) {
    modal.style.display = 'flex';

    // Buscar os dados da tarefa no banco para editar
    fetch('getTaskDetails.jsp?id=' + id)
        .then(response => response.json())
        .then(data => {
            // Preencher os campos com os dados da tarefa
            document.getElementById('idInput').value = data.id;
            document.getElementById('description').value = data.descricao;
            document.getElementById('priority').value = data.prioridade;
            document.getElementById('deadLine').value = data.prazo;
            document.getElementById('column').value = data.coluna;

            // Habilitar o select de coluna no modo de edição
            column.disabled = false;

            // Ajustar o action para quadroUpdate.jsp (edição de tarefa)
            taskForm.action = 'quadroUpdate.jsp';

            // Mostrar elementos do modo de edição
            creationModeTitle.style.display = "none";
            creationModeButton.style.display = "none";

            editingModeTitle.style.display = "block";
            editingModeButton.style.display = "block";
        })
        .catch(error => console.error('Erro ao buscar os dados da tarefa:', error));
}

function closeModal() {
    modal.style.display = 'none';
}