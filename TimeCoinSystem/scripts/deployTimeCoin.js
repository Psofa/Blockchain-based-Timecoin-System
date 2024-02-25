const hre = require("hardhat");

async function deployTimeCoin() {
  const contract = await hre.ethers.deployContract("TimeCoin");

  console.log("Deploying...");
  await contract.waitForDeployment();
  console.log("TimeCoin deployed to:", await contract);
}

// We recommend this pattern to be able to use async/await everywhere
// and properly handle errors.
deployTimeCoin().catch((error) => {
  console.error(error);
  process.exitCode = 1;
});
