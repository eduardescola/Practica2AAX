window.onload = closeChatForm;

//DOM elements

const selectButton = document.querySelector('#select_button');
const selectChatButton = document.querySelector('#select_chat_button');
const cancelButton = document.querySelector('#cancel_button');
const selectEmployeeForm = document.querySelector('.selectEmployeeForm');
const selectChatForm = document.querySelector('.selectChatForm');
const content = document.querySelector('.content');
const employeeList = document.querySelector('#employee_name');
const chatList = document.querySelector('#chat_name');

//FUNCTIONS

function hideForm() {	
	document.querySelector('.selectEmployeeForm').style.display = 'none';
	 selectEmployeeForm.reset();
	document.querySelector('.selectChatForm').style.display = 'none';
	 selectChatForm.reset();
}

function openChatForm() {
  document.getElementById("myForm").style.display = "block";
}

function closeChatForm() {
  document.getElementById("myForm").style.display = "none";
}


function createEmployeeElement(employee) {
	
    const employeeOption = document.createElement("option");
    employeeOption.setAttribute("id", employee.id);
    employeeOption.setAttribute("value", employee.id);
    employeeOption.setAttribute("class", "employee_name");
    employeeOption.innerHTML = employee.name;
    employeeList.appendChild(employeeOption);
}

function createChatElement(chat) {
	
    const chatOption = document.createElement("option");
    chatOption.setAttribute("id", chat.id);
    chatOption.setAttribute("value", chat.id);
    chatOption.setAttribute("class", "chat_name");
    chatOption.innerHTML = chat.name;
    chatList.appendChild(chatOption);
}


function onMessage(event) {
    const message = JSON.parse(event.data);
    if (message.action === "select") {
        document.getElementById(message.id);
    }
    
    if(message.action === "addChats"){
		createChatElement(message);
	}
    	
    if (message.action === "add") {
       createEmployeeElement(message);
    }
}



//HANDLERS

function handleShowFormButton() {
	selectEmployeeForm.style.display = '';
}

function handleCancelButton() {
	selectEmployeeForm.style.display = 'none';
    selectEmployeeForm.reset();
}


//LISTENERS

const socket = new WebSocket("ws://localhost:8080/websocketexample/actions");
socket.onmessage = onMessage;

cancelButton.addEventListener('click', handleCancelButton);

selectButton.addEventListener('click', () => {
    	const EmployeeAction = {
        	action: "select",
        	id: parseInt(employeeList.value)
    	};
    	socket.send(JSON.stringify(EmployeeAction));
});

selectChatButton.addEventListener('click', () => {
    const ChatAction = {
        action: "select",
        id: parseInt(chatList.value)
    };
    socket.send(JSON.stringify(ChatAction));
    hideForm();
    openChatForm();
    
});
