const { getNamedAccounts, ethers } = require("hardhat");
const { contractAddress, contractABI } = require("../constants.js");

async function transfer() {
  // Get the contract provider
  const { deployer } = await getNamedAccounts();
  const provider = await ethers.getSigner(deployer);

  // Get the contract instance
  const timeCoinContract = new ethers.Contract(
    contractAddress,
    contractABI,
    provider
  );

  // Transfer TimeCoins
  const fromUserId = "user1"; // Set the sender user ID
  const toUserId = "user2"; // Set the receiver user ID
  const amount = 1; // Set the amount to transfer

  const tx = await timeCoinContract.transfer(fromUserId, toUserId, amount);
  await tx.wait();

  console.log(
    `${amount} TimeCoins transferred from ${fromUserId} to ${toUserId}`
  );
}

transfer()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error(error);
    process.exit(1);
  });
