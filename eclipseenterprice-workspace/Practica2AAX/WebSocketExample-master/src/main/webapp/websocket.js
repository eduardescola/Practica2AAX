window.onload = closeChatForm;

//DOM elements

const selectButton = document.querySelector('#select_button');
const selectChatButton = document.querySelector('#select_chat_button');
const sendButton = document.querySelector('#send_button');
const closeButton = document.querySelector('#close_button');
const selectEmployeeForm = document.querySelector('.selectEmployeeForm');
const selectChatForm = document.querySelector('.selectChatForm');
const employeeList = document.querySelector('#employee_name');
const chatList = document.querySelector('#chat_name');
const messageText = document.querySelector('#msg');
const messageList = document.querySelector('#msgList');
const password = document.querySelector('#employee_password');
const chatTitle = document.querySelector('#chatTitle');
let chatSelected = 0;
let senderSelected = 0;
let senderName = 0;
let chatName = 0;

//FUNCTIONS

function closeSelectEmployeeForm(){
	document.querySelector('.selectEmployeeForm').style.display = 'none';
	selectEmployeeForm.reset();
}

function openSelectChatForm() {
	document.querySelector('.selectChatForm').style.display = "block";
}

function closeSelectChatForm() {
	document.querySelector('.selectChatForm').style.display = 'none';
	selectChatForm.reset();
}

function openChatForm() {
	document.getElementById("myForm").style.display = "block";
}

function closeChatForm() {
	document.querySelector('.selectChatForm').style.display = "none";
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

function showMessage(msg) {
	if (chatSelected == msg.id) {
		const messageLine = document.createElement("span");

		messageLine.innerHTML = (msg.name + ": " + msg.message);
		messageList.appendChild(messageLine);
		messageList.appendChild(document.createElement("br"));
	}
}



function onMessage(event) {
	const message = JSON.parse(event.data);
	if (message.action === "select") {

		document.getElementById(message.id);
	}

	if (message.action === "addChats") {
		createChatElement(message);
	}

	if (message.action === "add") {
		createEmployeeElement(message);
	}

	if (message.action === "showMessage") {
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
		id: parseInt(employeeList.value),
		psw: employee_password.value
	};
	senderSelected = employeeList.value;
	for (let i = 0; i < employeeList.childNodes.length; i++) {
		if (senderSelected == employeeList.childNodes[i].id) {
			senderName = employeeList.childNodes[i].textContent;
		}
	}
	socket.send(JSON.stringify(EmployeeAction));
	const chatNodes = (chatList.childNodes).length;
	for (let i = 0; i < chatNodes; i++) {
		chatList.removeChild(chatList.childNodes[0]);
	}
	openSelectChatForm();
	
});

selectChatButton.addEventListener('click', () => {
	const ChatAction = {
		action: "selectChat",
		id: parseInt(chatList.value)

	};
	chatSelected = chatList.value;
	for (let i = 0; i < chatList.childNodes.length; i++) {
		if (chatSelected == chatList.childNodes[i].id) {
			chatName = chatList.childNodes[i].textContent;
		}
	}
	socket.send(JSON.stringify(ChatAction));
	closeSelectEmployeeForm();
	closeSelectChatForm();
	openChatForm();

	const titleLine = document.createElement("span");
	titleLine.innerHTML = chatName;
	chatTitle.appendChild(titleLine);

});

messageText.addEventListener('keyup', (e) => {
	if (e.code === "Enter") {
		const SendAction = {
			action: "send",
			id: parseInt(chatSelected),
			sender: parseInt(senderSelected),
			name: senderName,
			msg: messageText.value
		};
		if (messageText.value != "") {
			socket.send(JSON.stringify(SendAction));
			messageText.value = "";
		}
	}
});

sendButton.addEventListener('click', () => {
	const SendAction = {
		action: "send",
		id: parseInt(chatSelected),
		sender: parseInt(senderSelected),
		name: senderName,
		msg: messageText.value
	};
	if (messageText.value != "") {
		socket.send(JSON.stringify(SendAction));
		messageText.value = "";
	}
});

closeButton.addEventListener('click', () => {
	location.reload();

});

addEventListener('keyup', (e) => {
	if (e.code === "Escape")
		location.reload();
});
