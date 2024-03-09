const { getNamedAccounts, ethers } = require("hardhat");

async function main() {
    const { getNamedAccounts, ethers } = require("hardhat");

    async function main() {
      // 1. Get the contract deployer
      const { deployer } = await getNamedAccounts();
    
      // 2. Get a reference to the TimeCoin contract Factory
      const timeCoinFactory = await ethers.getContractFactory("TimeCoin", deployer);
    
      // 3. Get the address of your deployed TimeCoin contract 
      const timeCoinContractAddress = "0x..."; // Replace with your actual contract address
    
      // 4. Get a reference to the deployed TimeCoin contract instance
      const timeCoinContract = timeCoinFactory.attach(timeCoinContractAddress);
    
      // 5 . Mint TimeCoins
      const userId = "user123"; // Set the desired user ID
      const amount = 100; // Set the amount of TimeCoins to mint
    
      const tx = await timeCoinContract.mint(userId, amount);
      await tx.wait();
    
      console.log(`Minted ${amount} TimeCoins to ${userId}`);
    }
    
    main()
      .then(() => process.exit(0))
      .catch((error) => {
        console.error(error);
        process.exit(1);
      });
    
    
}

main()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error(error);
    process.exit(1);
  });
