const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const jwt = require('jsonwebtoken');

const app = express();

app.use(bodyParser.json());
app.use(cors()); // 使用 cors 中间件处理跨域请求

// 模拟的用户数据
const responseData = JSON.stringify({
  users: [
    { id: 1, username: "tang", password: "123" }
  ],
  tabledate:
    {
      total:6,
      rows:[{
        id: 5,
        title: "打扫卫生",
        quota: 123,
        deadline: "2022-12-23T12:12:12",
        date: "2022-12-24",
        begin: "12:34:23",
        end: "12:56:12",
        address: "重庆市",
        oldId: 1,
        phone: "123",
        description: "的哈苏地方",
        status: 1,
        administratorId: 1,
        createTime: "2024-03-10T15:23:04",
        updateTime: "2024-03-10T15:23:04",
        message: null,
        remain: 123
      },
      {
        id: 6,
        title: "打扫卫生",
        quota: 123,
        deadline: "2022-12-23T12:12:12",
        date: "2022-12-24",
        begin: "12:34:23",
        end: "12:56:12",
        address: "重庆市",
        oldId: 1,
        phone: "123",
        description: "的哈苏地方",
        status: 1,
        administratorId: 1,
        createTime: "2024-03-10T15:23:04",
        updateTime: "2024-03-10T15:23:04",
        message: null,
        remain: 123
      },
      {
        id: 7,
        title: "打扫卫生",
        quota: 123,
        deadline: "2022-12-23T12:12:12",
        date: "2022-12-24",
        begin: "12:34:23",
        end: "12:56:12",
        address: "重庆市",
        oldId: 1,
        phone: "123",
        description: "的哈苏地方",
        status: 1,
        administratorId: 1,
        createTime: "2024-03-10T15:23:04",
        updateTime: "2024-03-10T15:23:04",
        message: null,
        remain: 123
      },
      {
        id: 8,
        title: "打扫卫生",
        quota: 123,
        deadline: "2022-12-23T12:12:12",
        date: "2022-12-24",
        begin: "12:34:23",
        end: "12:56:12",
        address: "重庆市",
        oldId: 1,
        phone: "123",
        description: "的哈苏地方",
        status: 1,
        administratorId: 1,
        createTime: "2024-03-10T15:23:04",
        updateTime: "2024-03-10T15:23:04",
        message: null,
        remain: 123
      },
      {
        id: 9,
        title: "打扫卫生",
        quota: 123,
        deadline: "2022-12-23T12:12:12",
        date: "2022-12-24",
        begin: "12:34:23",
        end: "12:56:12",
        address: "重庆市",
        oldId: 1,
        phone: "123",
        description: "的哈苏地方",
        status: 1,
        administratorId: 1,
        createTime: "2024-03-10T15:23:04",
        updateTime: "2024-03-10T15:23:04",
        message: null,
        remain: 123
      },
      {
        id: 10,
        title: "打扫卫生",
        quota: 123,
        deadline: "2022-12-23T12:12:12",
        date: "2022-12-24",
        begin: "12:34:23",
        end: "12:56:12",
        address: "重庆市",
        oldId: 1,
        phone: "123",
        description: "的哈苏地方",
        status: 1,
        administratorId: 1,
        createTime: "2024-03-10T15:23:04",
        updateTime: "2024-03-10T15:23:04",
        message: null,
        remain: 123
      }]
    }
});

app.post('/login', (req, res) => {
  try {
    const { username, password } = req.body;
    const users = JSON.parse(responseData).users;
    // 在用户数组中查找匹配的用户
    const user = users.find(user => user.username === username && user.password === password);

    if (user) {
      // 登录成功后，生成 token 并返回给客户端
      const token = jwt.sign({ username }, '123456', { expiresIn: '1h' });
      res.status(200).json({ code: 1, msg: "success", data: token });
    } else {
      res.status(200).json({ code: 0, msg: 'Invalid username or password' });
    }
  } catch (error) {
    res.status(400).json({ code: 0, msg: 'Invalid request body' });
  }
});

app.post('/register', (req, res) => {
  try {
    const userData = req.body;
    // 这里执行注册逻辑，将用户信息存入数据库或其他持久化方式
    res.status(200).json({ code: 1, msg: "success", data: null });
  } catch (error) {
    res.status(400).json({ code: 0, msg: 'Invalid request body' });
  }
});

app.get('/info', (req, res) => {
  try {
    const authHeader = req.headers.authorization;
    if (!authHeader) {
      // 如果请求头中没有 Authorization 头，返回未授权的响应
      return res.status(401).json({ code: 0, msg: 'Unauthorized' });
    }
    
    // 从请求头中获取 token
    const token = authHeader.split(' ')[1]; 
    
    let decodedToken;
    try {
      // 使用密钥解码 token
      decodedToken = jwt.verify(token, '123456'); 
    } catch (error) {
      // 如果解码失败，返回无效 token 的响应
      return res.status(401).json({ code: 0, msg: 'Invalid token' });
    }

    // 如果解码成功，继续处理业务逻辑
    // 这里可以根据解码出来的信息做一些操作，例如从数据库中查询用户信息等
    res.status(200).json({ code: 1, msg: 'success', data: decodedToken.username });
  } catch (error) {
    // 如果出现其他错误，返回服务器错误的响应
    res.status(500).json({ code: 0, msg: 'Server error' });
  }
});

app.get('/administrator', (req, res) => {
  try {
    const authHeader = req.headers.authorization;
    if (!authHeader) {
      // 如果请求头中没有 Authorization 头，返回未授权的响应
      return res.status(401).json({ code: 0, msg: 'Unauthorized' });
    }
    
    // 从请求头中获取 token
    const token = authHeader.split(' ')[1]; 
    
    let decodedToken;
    try {
      // 使用密钥解码 token
      decodedToken = jwt.verify(token, '123456'); 
    } catch (error) {
      // 如果解码失败，返回无效 token 的响应
      return res.status(401).json({ code: 0, msg: 'Invalid token' });
    }
    // 如果解码成功，继续处理业务逻辑
    const data = JSON.parse(responseData).tabledate;
    res.status(200).json({ code: 1, msg: 'success', data: data });
  } catch (error) {
    // 如果出现其他错误，返回服务器错误的响应
    res.status(500).json({ code: 0, msg: 'Server error' });
  }
});

app.delete('/administrator/:ids', (req, res) => {
  try {
    const authHeader = req.headers.authorization;
    if (!authHeader) {
      return res.status(401).json({ code: 0, msg: 'Unauthorized' });
    }

    const token = authHeader.split(' ')[1];
    
    let decodedToken;
    try {
      decodedToken = jwt.verify(token, '123456');
    } catch (error) {
      return res.status(401).json({ code: 0, msg: 'Invalid token' });
    }

    const activityIds = req.params.ids.split(','); // 从请求参数中获取活动ID数组
    // 在这里可以使用活动ID数组执行相应的操作，比如从数据库中删除对应的活动记录等
    res.status(200).json({ code: 1, msg: 'success', data: null });
  } catch (error) {
    res.status(500).json({ code: 0, msg: 'Server error' });
  }
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});

