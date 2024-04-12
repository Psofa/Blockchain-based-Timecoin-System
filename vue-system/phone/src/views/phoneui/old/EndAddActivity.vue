<template>
    <div>
        <el-result icon="success" title="成功提交" subTitle="请根据提示进行操作">
            <template slot="extra">
                <el-button type="primary" size="medium" @click="pushBack">返回</el-button>
            </template>
        </el-result>
    </div>
</template>

<script>
export default {
    name: 'EndAddActivity',
    methods: {
        pushBack() {
            this.$router.push('/serverOld');
        }
    }
};

</script>