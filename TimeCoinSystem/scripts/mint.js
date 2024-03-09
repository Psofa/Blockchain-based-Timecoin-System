const { getNamedAccounts, ethers } = require("hardhat");
const { contractAddress, contractABI } = require("../constants.js");

async function mint() {
  // Get the contract provider
  const { deployer } = await getNamedAccounts();
  const provider = await ethers.getSigner(deployer);

  // Get the contract instance
  const timeCoinContract = new ethers.Contract(
    contractAddress,
    contractABI,
    provider
  );

  // Mint TimeCoins
  const userId = "user1"; // Set the desired user ID
  const amount = 1; // Set the amount of TimeCoins to mint

  const tx = await timeCoinContract.mint(userId, amount);
  await tx.wait();

  console.log(`Minted ${amount} TimeCoins to ${userId}`);
}

mint()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error(error);
    process.exit(1);
  });
