pragma solidity ^0.8.0;

/**
 * @title TimeCoinReward contract 1.0
 * @author Bard
 * @notice This contract is for calculating time coin rewards based on activities.
 */
contract TimeCoinReward {
    // 管理员地址
    address public owner;

    // 活动类型系数
    mapping(string => uint256) public activityTypeFactors;

    // 活动时长系数
    mapping(uint256 => uint256) public activityDurationFactors;

    // 活动完成质量系数
    mapping(string => uint256) public activityCompletionQualityFactors;

    // 志愿者贡献度系数
    mapping(string => uint256) public volunteerContributionFactors;

    // 用户的时间币余额
    mapping(string => uint256) public balances;

    // 事件
    event Reward(string userId, uint256 amount);
    event Transfer(string fromUserId, string toUserId, uint256 amount);

    // 构造函数，设置合约的拥有者
    constructor() {
        owner = msg.sender;

        // 初始化活动类型系数
        activityTypeFactors["体力劳动"] = 100;
        activityTypeFactors["知识技能分享"] = 150;
        activityTypeFactors["文艺演出"] = 120;
        activityTypeFactors["其他"] = 100;

        // 初始化活动时长系数
        activityDurationFactors[1] = 50;
        activityDurationFactors[2] = 80;
        activityDurationFactors[3] = 100;
        activityDurationFactors[4] = 120;
        activityDurationFactors[5] = 150;

        // 初始化活动完成质量系数
        activityCompletionQualityFactors["优秀"] = 120;
        activityCompletionQualityFactors["良好"] = 100;
        activityCompletionQualityFactors["一般"] = 80;
        activityCompletionQualityFactors["较差"] = 50;

        // 初始化志愿者贡献度系数
        volunteerContributionFactors["积极主动，表现突出"] = 120;
        volunteerContributionFactors["认真负责，完成任务"] = 100;
        volunteerContributionFactors["参与度高，配合良好"] = 80;
        volunteerContributionFactors["参与度低，需改进"] = 50;
    }

    // 确保只有合约拥有者可以调用某个函数
    modifier onlyOwner() {
        require(msg.sender == owner, "Only the contract owner can call this function.");
        _;
    }

    // 设置活动类型系数
    function setActivityTypeFactor(string memory activityType, uint256 factor) public onlyOwner {
        require(factor > 0, "Factor must be greater than 0");
        activityTypeFactors[activityType] = factor;
    }

    // 设置活动时长系数
    function setActivityDurationFactor(uint256 duration, uint256 factor) public onlyOwner {
        require(factor > 0, "Factor must be greater than 0");
        activityDurationFactors[duration] = factor;
    }

    // 设置活动完成质量系数
    function setActivityCompletionQualityFactor(string memory quality, uint256 factor) public onlyOwner {
        require(factor > 0, "Factor must be greater than 0");
        activityCompletionQualityFactors[quality] = factor;
    }

    // 设置志愿者贡献度系数
    function setVolunteerContributionFactor(string memory contribution, uint256 factor) public onlyOwner {
        require(factor > 0, "Factor must be greater than 0");
        volunteerContributionFactors[contribution] = factor;
    }

    // 计算时间币奖励
    function calculateReward(
        string memory activityType,
        uint256 duration,
        string memory quality,
        string memory contribution
    ) public view returns (uint256) {
        return
            activityTypeFactors[activityType] *
            activityDurationFactors[duration] *
            activityCompletionQualityFactors[quality] *
            volunteerContributionFactors[contribution];
    }

    // 发放时间币奖励
    function reward(
        string memory userId,
        string memory activityType,
        uint256 duration,
        string memory quality,
        string memory contribution
    ) public onlyOwner {
        uint256 rewardAmount = calculateReward(activityType, duration, quality, contribution);
        balances[userId] += rewardAmount;
        
