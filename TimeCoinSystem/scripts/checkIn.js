const hre = require("hardhat");

const contractABI = []; // 请填写合约 ABI
const contractAddress = ""; // 请填写合约地址
const contract = new ethers.Contract(
  contractAddress,
  contractABI,
  hre.ethers.provider
);

const amount = 10;

function checkIn(userId) {
  // 调用智能合约的mint函数发放时间币
  contract.mint(userId, amount);
  return true;
}

checkIn("user1");