//DOM elements

const showFormButton = document.querySelector('.addDevice .button a');
const selectButton = document.querySelector('#select_button');
const cancelButton = document.querySelector('#cancel_button');
const addDeviceForm = document.querySelector('.addDeviceForm');
const content = document.querySelector('.content');
const employeeList = document.querySelector('#employee_name');

//FUNCTIONS

function hideForm() {	
	document.querySelector('.addDeviceForm').style.display = 'none';
}


function createEmployeeElement(employee) {
	
    const employeeOption = document.createElement("option");
    employeeOption.setAttribute("id", employee.id);
    employeeOption.setAttribute("value", employee.id);
    employeeOption.setAttribute("class", "employee_name");
    employeeOption.innerHTML = employee.name;
    employeeList.appendChild(employeeOption);
}


function onMessage(event) {
    const message = JSON.parse(event.data);
    if (message.action === "select") {
        document.getElementById(message.id);
    }
    
    if(message.action === "addChats"){
		message.id
		message
	}
    	
    if (message.action === "add") {
       createEmployeeElement(message);
    }
}



//HANDLERS

function handleShowFormButton() {
	addDeviceForm.style.display = '';
}

function handleCancelButton() {
	addDeviceForm.style.display = 'none';
    addDeviceForm.reset();
}


//LISTENERS

const socket = new WebSocket("ws://localhost:8080/websocketexample/actions");
socket.onmessage = onMessage;

showFormButton.addEventListener('click', handleShowFormButton);
cancelButton.addEventListener('click', handleCancelButton);

selectButton.addEventListener('click', () => {
    	const EmployeeAction = {
        	action: "select",
        	id: parseInt(employeeList.value)
    	};
    	socket.send(JSON.stringify(EmployeeAction));
});
