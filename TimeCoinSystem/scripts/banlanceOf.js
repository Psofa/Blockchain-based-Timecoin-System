const { getNamedAccounts, ethers } = require("hardhat");
const { contractAddress, contractABI } = require("../constants.js");

async function balanceOf() {
  // Get the contract provider
  const { deployer } = await getNamedAccounts();
  const provider = await ethers.getSigner(deployer);

  // Get the contract instance
  const timeCoinContract = new ethers.Contract(
    contractAddress,
    contractABI,
    provider
  );

  // BalanceOf TimeCoins
  const userId = "user1"; // Set the desired user ID

  const balance = await timeCoinContract.balanceOf(userId);

  console.log(`Balance of ${userId}: ${balance.toString()}`);
}

balanceOf()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error(error);
    process.exit(1);
  });
