var ws;
const textBox = document.getElementById("msg")
const chat = document.getElementById("log");

function connect() {
    var username = new URLSearchParams(window.location.search).get('username'); // get username from url parameters
    var url = "ws://localhost:8080/websocket/" + username;
    //var url = "ws://echo.websocket.org";

    ws = new WebSocket(url);

    ws.onmessage = function(event) { // Called when client receives a message from the server
        console.log(event.data);
        if (!event.data.includes('userList'))  {

          // display on browser
          if (event.data.includes('connected') || event.data.includes('disconnected')) {
            outputMessage("<span style='font-weight: bold'>Server</span>: " + event.data);
          }
          else {
            outputMessage(event.data);
          }


        }
        else {
          console.log(event.data);
          updateUserList(event.data);
        }
    };

    ws.onopen = function(event) { // called when connection is opened
        outputMessage("Connected to RedPen chat");
    };
}

function send() {  // this is how to send messages
    var content = textBox.value;
    textBox.value = "";
    textBox.focus();
    ws.send(content);
}

function outputMessage(msg) {
  var p = document.createElement('p');
  p.innerHTML = msg;
  chat.appendChild(p);
  chat.scrollTop = chat.scrollHeight; // automatically scroll to bottom of chat messages
}

function updateUserList(usernames) {
  var nameList = usernames.split('\n');
  var list = document.getElementById("list");
  list.innerHTML = "";

  for (var i = 1; i < nameList.length - 1; i++) {
    var li = document.createElement('li');
    li.innerHTML = nameList[i];
    list.appendChild(li);
  }
}

document.addEventListener('keydown', function(event)  {
  if (event.keyCode == 13)  {
    sendChat.click();
  }
}, true);
