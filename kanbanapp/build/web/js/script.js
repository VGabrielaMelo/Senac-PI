/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
        let label = document.querySelectorAll('label').
        forEach(label => {
            label.innerHTML = label.innerText.split('').map((letters, i) => `<span style="transition-delay: ${i*50}ms">${letters}</span>`).join('');
        });
        
function enviarDadosRegistro(event) {
    event.preventDefault(); // Impede o envio padrão do formulário

    // Coleta dos dados do formulário
    const nome = document.getElementById('nome').value;
    const usuario = document.getElementById('usuario').value;
    const senha = document.getElementById('senha').value;
    const senha2 = document.getElementById('senha2').value;

    // Verificação das senhas
    if (senha !== senha2) {
        Swal.fire({
            title: 'Erro',
            text: 'As senhas não coincidem. Por favor, verifique e tente novamente.',
            icon: 'error',
            confirmButtonText: 'OK'
        });
        return;
    }
    
    if (nome === '' || usuario === '' || senha === '' || senha2 === '') {
        Swal.fire({
            title: 'Erro',
            text: 'Por favor, preencha todos os campos.',
            icon: 'info',
            confirmButtonText: 'OK'
        });
        return; // Impede o envio da requisição AJAX
    }

    // Configura a requisição AJAX
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "registroSubmit.jsp", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            try {
                // Fazer o parse da resposta JSON
                const response = JSON.parse(xhr.responseText);

                // Verificar o status da resposta
                if (response.status === "error") {
                    Swal.fire({
                        title: 'Erro',
                        text: response.message,
                        icon: 'error',
                        confirmButtonText: 'OK'
                    });
                } else if (response.status === "success") {
                    Swal.fire({
                        title: 'Cadastro',
                        text: response.message,
                        icon: 'success',
                        confirmButtonText: 'OK'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            window.location.href = "index.jsp";
                        }
                    });
                } else {
                    Swal.fire({
                        title: 'Erro',
                        text: 'Resposta desconhecida do servidor.',
                        icon: 'error',
                        confirmButtonText: 'OK'
                    });
                }
            } catch (e) {
                Swal.fire({
                    title: 'Erro',
                    text: 'Erro ao processar a resposta do servidor.',
                    icon: 'error',
                    confirmButtonText: 'OK'
                });
                console.error('Erro de parsing JSON:', e);
            }
        } else if (xhr.readyState === 4) {
            // Exibir mensagem de erro se houver problema na requisição
            Swal.fire({
                title: 'Erro',
                text: 'Houve um problema ao se conectar ao servidor. Tente novamente mais tarde.',
                icon: 'error',
                confirmButtonText: 'OK'
            });
        }
    };

    // Enviar dados para o servidor
    const params = `nome=${encodeURIComponent(nome)}&usuario=${encodeURIComponent(usuario)}&senha=${encodeURIComponent(senha)}`;
    xhr.send(params);
}
    
    function enviarLogin(event) {
    event.preventDefault(); // Impede o envio padrão do formulário

    // Coleta dos dados do formulário
    const usuario = document.getElementById('usuario').value;
    const senha = document.getElementById('senha').value;
    
    if (usuario === '' || senha === '') {
        Swal.fire({
            title: 'Erro',
            text: 'Por favor, preencha todos os campos.',
            icon: 'info',
            confirmButtonText: 'OK'
        });
        return; // Impede o envio da requisição AJAX
    }

    // Configura a requisição AJAX
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "loginSubmit.jsp", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const resposta = xhr.responseText.trim();  // Obtém e remove espaços em branco

            if (resposta === "success") {
                // Redireciona para quadro.jsp se o login for bem-sucedido
                window.location.href = "quadro.jsp";
            } else if (resposta === "fail") {
                // Exibe SweetAlert para login inválido
                Swal.fire({
                    title: 'Erro',
                    text: 'Usuário ou senha inválidos.',
                    icon: 'error',
                    confirmButtonText: 'OK'
                });
            } else {
                // Exibe SweetAlert para erro geral
                Swal.fire({
                    title: 'Erro',
                    text: 'Ocorreu um erro. Tente novamente mais tarde.',
                    icon: 'error',
                    confirmButtonText: 'OK'
                });
            }
        }
    };

    // Envio dos dados
    xhr.send(`usuario=${encodeURIComponent(usuario)}&senha=${encodeURIComponent(senha)}`);
}

    