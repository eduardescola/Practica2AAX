window.onload = closeSelectChatForm;
//window.onload =openChatForm;
//DOM elements

const selectButton = document.querySelector('#select_button');
const selectChatButton = document.querySelector('#select_chat_button');
const sendButton = document.querySelector('#send_button');
const selectEmployeeForm = document.querySelector('.selectEmployeeForm');
const selectChatForm = document.querySelector('.selectChatForm');
const content = document.querySelector('.content');
const employeeList = document.querySelector('#employee_name');
const chatList = document.querySelector('#chat_name');
const messageText = document.querySelector('#msg');
const messageList = document.querySelector('#msgList');

//FUNCTIONS

function hideForm() {	
	document.querySelector('.selectEmployeeForm').style.display = 'none';
	 selectEmployeeForm.reset();
	document.querySelector('.selectChatForm').style.display = 'none';
	 selectChatForm.reset();
}

function openSelectChatForm() {
  document.querySelector('.selectChatForm').style.display = "block";
}

function closeSelectChatForm() {
  document.querySelector('.selectChatForm').style.display = "none";
  document.getElementById("myForm").style.display = "none";
}

function openChatForm() {
  document.getElementById("myForm").style.display = "block";
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

function showMessage(msg){
	if (chatList.value == msg.id){
		const messageLine = document.createElement("span");
		
		messageLine.innerHTML = (employeeList.name +": "+msg.message);
		messageList.appendChild(messageLine);
		messageList.appendChild(document.createElement("br"));
	}
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
    
    if(message.action === "showMessage"){
		showMessage(message);
	}
}



//HANDLERS

function handleShowFormButton() {
	selectEmployeeForm.style.display = '';
}


//LISTENERS

const socket = new WebSocket("ws://localhost:8080/websocketexample/actions");
socket.onmessage = onMessage;

selectButton.addEventListener('click', () => {
    	const EmployeeAction = {
        	action: "select",
        	id: parseInt(employeeList.value)
    	};
    	socket.send(JSON.stringify(EmployeeAction));
    	const chatNodes=(chatList.childNodes).length;
    	for(let i=0; i<chatNodes; i++){
			chatList.removeChild(chatList.childNodes[0]);
		}
    	openSelectChatForm();
    	
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

sendButton.addEventListener('click', () => {
    const SendAction = {
        action: "send",
        id: parseInt(chatList.value),
        sender: parseInt(employeeList.value),
        msg: messageText.value
    };
    socket.send(JSON.stringify(SendAction));
    messageText.value="";
});
