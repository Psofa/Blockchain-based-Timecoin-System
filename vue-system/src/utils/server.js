// // 引入 Node.js 内置的 http 模块
// const http = require('http');

// // 创建服务器
// const server = http.createServer((req, res) => {
//   // 设置响应头
//   res.writeHead(200, { 'Content-Type': 'text/plain' });

//   // 发送响应数据
//   res.end('Hello, World!\n');
// });

// // 设置服务器监听的端口号
// const PORT = process.env.PORT || 3000;

// // 服务器开始监听指定的端口
// server.listen(PORT, () => {
//   console.log(`Server is running on port ${PORT}`);
// });

// const http = require('http');
// // 模拟的用户数据
// const responseData = JSON.stringify({
//   users: [
//     { id: 1, username: "tang", password: "123", token: '123456' }
//   ]
// });
// const server = http.createServer((req, res) => {
//   if (req.method === 'OPTIONS') {
//     res.setHeader('Access-Control-Allow-Origin', 'http://localhost:7000');
//     res.setHeader('Access-Control-Allow-Methods', 'GET, POST');
//     res.setHeader('Access-Control-Allow-Headers', 'Content-Type, Authorization');
//     res.writeHead(200);
//     res.end();
//     return;
//   }

//   res.setHeader('Access-Control-Allow-Origin', 'http://localhost:7000');
//   res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS');
//   res.setHeader('Access-Control-Allow-Headers', 'Content-Type');
//   res.setHeader('Content-Type', 'application/json; charset=utf-8');

//   if (req.method === 'POST') {
//     let body = '';
//     req.on('data', chunk => {
//       body += chunk.toString();
//     });
//     req.on('end', () => {
//       try {
//         const userData = JSON.parse(body);
//         const users = JSON.parse(responseData).users;
//         const user = users.find(user => user.username === userData.username && user.password === userData.password);
//         if (user) {
//           res.writeHead(200);
//           res.end(JSON.stringify({ code: 1, msg: "success", data: user.token }));
//         } else {
//           res.writeHead(200);
//           res.end(JSON.stringify({ code: 0, msg: 'Invalid username or password' }));
//         }
//       } catch (error) {
//         res.writeHead(400);
//         res.end(JSON.stringify({ code: 0, message: 'Invalid request body' }));
//       }
//     });
//   } else {
//     res.writeHead(405);
//     res.end('Method Not Allowed');
//   }
// });

// const PORT = process.env.PORT || 3000;

// server.listen(PORT, () => {
//   console.log(`Server is running on port ${PORT}`);
// });

const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();

app.use(bodyParser.json());
app.use(cors()); // 使用 cors 中间件处理跨域请求

// 模拟的用户数据
const responseData = JSON.stringify({
  users: [
    { id: 1, username: "tang", password: "123", token: '123456' }
  ]
});

// 登录接口
app.post('/login', (req, res) => {
  try {
    const userData = req.body;
    const users = JSON.parse(responseData).users;
    const user = users.find(user => user.username === userData.username && user.password === userData.password);
    if (user) {
      res.status(200).json({ code: 1, msg: "success", data: user.token });
    } else {
      res.status(200).json({ code: 0, msg: 'Invalid username or password' });
    }
  } catch (error) {
    res.status(400).json({ code: 0, msg: 'Invalid request body' });
  }
});

// 注册接口
app.post('/register', (req, res) => {
  try {
    const userData = req.body;
    // 这里执行注册逻辑，将用户信息存入数据库或其他持久化方式
    res.status(200).json({ code: 1, msg: "success", data: null });
  } catch (error) {
    res.status(400).json({ code: 0, msg: 'Invalid request body' });
  }
});


const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});

