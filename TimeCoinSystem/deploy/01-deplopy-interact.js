const { network } = require("hardhat");

module.exports = async ({ getNamedAccounts, deployments }) => {
  const { deploy, log } = deployments;
  const { deployer } = await getNamedAccounts();
  const chainId = network.config.chainId;

  const timeCoin = await deploy("TimeCoin", {
    from: deployer,
    log: true,
  });

  log("------------------------------------------");
};

module.exports.tags = ["all", "TimeCoin"];
