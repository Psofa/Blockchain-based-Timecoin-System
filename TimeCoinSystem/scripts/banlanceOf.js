async function balanceOf() {
  const { getNamedAccounts, ethers } = require("hardhat");

  // 1. Get the contract deployer
  const { deployer } = await getNamedAccounts();
  const provider = await ethers.getSigner(deployer);

  // 2. Get a reference to the TimeCoin contract Factory
  const timeCoinFactory = await ethers.getContractFactory("TimeCoin", provider);

  // 3. Get the address of your deployed TimeCoin contract
  const timeCoinContractAddress = "0x5FbDB2315678afecb367f032d93F642f64180aa3"; // Replace with your actual contract address

  // 4. Get a reference to the deployed TimeCoin contract instance
  const timeCoinContract = timeCoinFactory.attach(timeCoinContractAddress);

  // 5 . BalanceOf TimeCoins
  const userId = "user2"; // Set the desired user ID

  const balance = await timeCoinContract.balanceOf(userId);

  console.log(`Balance of ${userId}: ${balance.toString()}`);
}

balanceOf()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error(error);
    process.exit(1);
  });
