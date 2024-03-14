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
  },
  usersInfo: {
    total: 6,
    rows: [{
      id: 1,
      username: "user1",
      password: "password1",
      role: 1,
      email: "user1@example.com",
      age: 21,
      phone: "12345678901",
      address: "Address 1",
      name: "Name 1",
      createTime: "2024-03-08T09:00:00.000Z"
    },
    {
      id: 2,
      username: "user2",
      password: "password2",
      role: 2,
      email: "user2@example.com",
      age: 22,
      phone: "12345678902",
      address: "Address 2",
      name: "Name 2",
      createTime: "2024-03-08T09:00:00.000Z"
    },
    {
      id: 3,
      username: "user3",
      password: "password3",
      role: 3,
      email: "user3@example.com",
      age: 23,
      phone: "12345678903",
      address: "Address 3",
      name: "Name 3",
      createTime: "2024-03-08T09:00:00.000Z"
    },
    {
      id: 4,
      username: "user4",
      password: "password4",
      role: 1,
      email: "user4@example.com",
      age: 24,
      phone: "12345678904",
      address: "Address 4",
      name: "Name 4",
      createTime: "2024-03-08T09:00:00.000Z"
    },
    {
      id: 5,
      username: "user5",
      password: "password5",
      role: 2,
      email: "user5@example.com",
      age: 25,
      phone: "12345678905",
      address: "Address 5",
      name: "Name 5",
      createTime: "2024-03-08T09:00:00.000Z"
    },
    {
      id: 6,
      username: "user6",
      password: "password6",
      role: 3,
      email: "user6@example.com",
      age: 26,
      phone: "12345678906",
      address: "Address 6",
      name: "Name 6",
      createTime: "2024-03-08T09:00:00.000Z"
    }]
  },
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
    const data = JSON.parse(responseData).users[0];
    res.status(200).json({ code: 1, msg: 'success', data: data });
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

app.delete('/administrator/users/:ids', (req, res) => {
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

app.get('/administrator/users', (req, res) => {
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
    
    const data = JSON.parse(responseData).usersInfo;
    res.status(200).json({ code: 1, msg: 'success', data: data });
  } catch (error) {
    res.status(500).json({ code: 0, msg: 'Server error' });
  }
});

app.post('/administrator/users', (req, res) => {
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
    
    const data = JSON.parse(responseData).usersInfo;
    res.status(200).json({ code: 1, msg: 'success', data: data });
  } catch (error) {
    res.status(500).json({ code: 0, msg: 'Server error' });
  }
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});

