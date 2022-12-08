window.onload = init;

//DOM elements

const showFormButton = document.querySelector('.addDevice .button a');
const selectButton = document.querySelector('#select_button');
const cancelButton = document.querySelector('#cancel_button');
const addDeviceForm = document.querySelector('.addDeviceForm');
const content = document.querySelector('.content');
const employeeList = document.querySelector("select");

//FUNCTIONS

function init() {	
	document.querySelector('.addDeviceForm').style.display = 'none';
}


function createEmployeeElement(employee) {
	
    const employeeOption = document.createElement("option");
    employeeOption.setAttribute("id", employee.id);
    employeeOption.setAttribute("class", "employee_name");
    employeeOption.innerHTML = employee.name;
    employeeList.appendChild(employeeOption);

    return employeeOption;
}


function onMessage(event) {
    const employee = JSON.parse(event.data);
    if (employee.action === "select") {
        document.getElementById(employee.id);
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

employeeList.addEventListener('click', e => {
	
	if(e.target.getAttribute('data-op') === 'select') {
    	const EmployeeAction = {
        	action: "select",
        	id: parseInt(e.target.id)
    	};
    	socket.send(JSON.stringify(EmployeeAction));
    }
});
