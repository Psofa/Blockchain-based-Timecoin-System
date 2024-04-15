<template>
  <div>
    <div id="container"></div>
    <div style="display: flex;margin-top: 10px;flex-direction: column;">
      <div style="margin: 20px;">
        当前位置：{{ address }}
      </div>
      <el-button type="primary" @click="signIn" style="margin: 20px;">签 到</el-button>
    </div>
  </div>
</template>

<script>
import AMapLoader from "@amap/amap-jsapi-loader";
import request from '@/utils/request';

export default {
  name: "SignInUser",
  data() {
    return {
      map: null,
      address: "",
      id: null,
    };
  },
  created() {
      // 从查询参数中获取数据
      this.id = parseInt(this.$route.query.id);
  },
  mounted() {
    const securityScript = document.createElement("script");
    securityScript.type = "text/javascript";
    securityScript.innerHTML = `
      window._AMapSecurityConfig = {
        securityJsCode: '0f44724be842da40ac3db0cc34868b70'
      };
    `;
    document.body.appendChild(securityScript);
    this.initAMap();
  },
  unmounted() {
    this.map?.destroy();
  },
  methods: {
    initAMap() {
      AMapLoader.load({
        key: "cc996da095298e80e521e0df7a3d8b38", // 申请好的Web端开发者Key，首次调用 load 时必填
        version: "1.4.15", // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
        plugins: ["AMap.Scale","AMap.Geocoder"], // 需要使用的的插件列表，如比例尺'AMap.Scale'，支持添加多个如：['...','...']
      })
        .then((AMap) => {
          // 获取用户位置并设置地图中心点
          navigator.geolocation.getCurrentPosition(
            (position) => {
              let { latitude, longitude } = position.coords;
              
              this.map = new AMap.Map("container", {
                // 设置地图容器id
                viewMode: "3D", // 是否为3D地图模式
                zoom: 11, // 初始化地图级别
                center: [longitude, latitude], // 将用户位置设为地图中心点
              });

              // 创建逆地理编码对象
              AMap.plugin('AMap.Geocoder', () => {
                const geocoder = new AMap.Geocoder({
                  city: null, // 城市设为全国，表示对用户所在位置的城市不做限制
                });

                // 逆地理编码，将经纬度坐标转换为地址信息
                geocoder.getAddress([longitude, latitude], (status, result) => {
                  if (status === "error") {
                    console.error("Failed to get address. Error:", result);
                  } else if (status === "complete" && result.info === "OK") {
                    this.address = result.regeocode.formattedAddress;
                  } else {
                    console.error("Failed to get address. Status:", status, "Result info:", result.info);
                  }
                });

              });
            },
            (error) => {
              console.error("Error getting user location:", error);
            }
          );
        })
        .catch((e) => {
          console.log(e);
        });
    },
    signIn() {
            const data = {
                id: this.id,
                address:this.address,
            };
            request.put(`users/vol/sign`,data)
                .then(response => {
                if (response.code === 1) {
                    this.$message.success(response.msg);
                    setTimeout(() => {
                        this.$router.push({ 
                            name: 'ActivityOfUser',
                            query: { 
                                id: this.id,
                            } 
                        });
                    }, 1000);
                } else {
                    this.$message.error(response.msg);
                }
                })
                .catch(error => {
                    console.error('取消失败:', error);
                });
        },
  },
};
</script>

<style scoped>
#container {
  width: 100%;
  height: 700px;
}
</style>
