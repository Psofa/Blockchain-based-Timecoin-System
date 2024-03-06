require("@nomicfoundation/hardhat-toolbox");
require('hardhat-deploy');

/** @type import('hardhat/config').HardhatUserConfig */
module.exports = {
  solidity: "0.8.0",
  namedAccounts: {
    deployer: {
      default: 0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266,
    },
  },
};
