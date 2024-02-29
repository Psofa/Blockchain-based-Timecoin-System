// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

/**@title TimeCoin system contract 1.0
 * @author 罗政
 * @notice This contract is for managing the time coin of users
 */
contract TimeCoin {
    // 管理员地址
    address public owner;

    // 用户的时间币余额
    mapping(string => uint256) private balances;

    // 事件
    event Transfer(string from, string to, uint256 value);
    event Mint(string to, uint256 value);

    // 构造函数，设置合约的拥有者
    constructor() {
        owner = msg.sender;
    }

    // 确保只有合约拥有者可以调用某个函数
    modifier onlyOwner() {
        require(
            msg.sender == owner,
            "Only the contract owner can call this function."
        );
        _;
    }

    // 发放时间币给指定用户
    function mint(string memory userId, uint256 amount) public onlyOwner {
        require(amount > 0, "Amount must be greater than 0");
        balances[userId] += amount;
        emit Mint(userId, amount);
    }

    // 在用户之间转移时间币
    function transfer(
        string memory fromUserId,
        string memory toUserId,
        uint256 amount
    ) public onlyOwner {
        require(amount <= balances[fromUserId], "Insufficient balance.");
        balances[fromUserId] -= amount;
        balances[toUserId] += amount;
        emit Transfer(fromUserId, toUserId, amount);
    }

    // 查询用户的时间币余额
    function balanceOf(string memory userId) public view returns (uint256) {
        return balances[userId];
    }
}
