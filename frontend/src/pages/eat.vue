<template>
    <div>
        <el-alert
                :title="'就餐时间：'+timeplan"
                :closable="false"
                type="success">
        </el-alert>
        <el-row :gutter="20" style="margin-top: 10px;">
            <el-col :xs="24" :lg="8">
                <el-card shadow="hover">
                    <div slot="header" class="clearfix">
                        <span>A2 2楼</span>
                    </div>
                    <el-table
                            v-loading="loading"
                            :data="a22f"
                            border
                            stripe>
                        <el-table-column
                                prop="key"
                                align="center"
                                showOverflowTooltip
                                width="200"
                                label="类型">
                        </el-table-column>
                        <el-table-column
                                prop="value"
                                showOverflowTooltip
                                label="名称">
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>
            <el-col :xs="24" :lg="8">
                <el-card shadow="hover">
                    <div slot="header" class="clearfix">
                        <span>A2 3楼</span>
                    </div>
                    <el-table
                            v-loading="loading"
                            :data="a23f"
                            border
                            stripe>
                        <el-table-column
                                prop="key"
                                align="center"
                                showOverflowTooltip
                                width="200"
                                label="类型">
                        </el-table-column>
                        <el-table-column
                                prop="value"
                                showOverflowTooltip
                                label="名称">
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>
            <el-col :xs="24" :lg="8">
                <el-card shadow="hover">
                    <div slot="header" class="clearfix">
                        <span>A5 5楼</span>
                    </div>
                    <el-table
                            v-loading="loading"
                            :data="a55f"
                            border
                            stripe>
                        <el-table-column
                                prop="key"
                                align="center"
                                showOverflowTooltip
                                width="200"
                                label="类型">
                        </el-table-column>
                        <el-table-column
                                prop="value"
                                showOverflowTooltip
                                label="名称">
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                a22f: [],
                a23f: [],
                a55f: [],
                loading: true,
                timeplan: ''
            }
        },
        mounted() {
            this.$resource('/eat/now-eat/menu-0620.json').get({}).then(res => {
                this.loading = false;
                const now = new Date();
                res.data.workDate.forEach((d, i) => {
                    if (d === this.$parseTime(now, '{y}/{m}/{d}')) {
                        res.data.nooning.a22f.forEach(item => {
                            this.a22f.push({
                                key: item.key.replace(/&ensp;/g, "").replace(/&emsp;/g, ""),
                                value: item.value[i] + (item.value[i + 5] ? '+' + item.value[i + 5] : '')
                            })
                        });
                        res.data.nooning.a23f.forEach(item => {
                            this.a23f.push({
                                key: item.key.replace(/&ensp;/g, "").replace(/&emsp;/g, ""),
                                value: item.value[i] + (item.value[i + 5] ? '+' + item.value[i + 5] : '')
                            })
                        });
                        res.data.nooning.a55f.forEach(item => {
                            this.a55f.push({
                                key: item.key.replace(/&ensp;/g, "").replace(/&emsp;/g, ""),
                                value: item.value[i] + (item.value[i + 5] ? '+' + item.value[i + 5] : '')
                            })
                        });
                    }
                });
                res.data.timeplan.forEach(item => {
                    if (item.key.indexOf("B") >= 0) {
                        this.timeplan = item.value;
                    }
                })
            }, e => {
                console.error(e);
            });
        }
    }
</script>
