<template>
    <div>
      <div id="mapContainer" style="position: relative;">
        <div id="myPageTop" style="position: absolute;right: 0; z-index: 1000;">
          <table style="background-color: white;">
            <tr>
              <td>
                <label>请输入关键字：</label>
              </td>
            </tr>
            <tr>
              <td>
                <input id="tipinput" v-model="keyword"/>
              </td>
            </tr>
          </table>
        </div>
        <div id="container" style="width: 100%; height: 500px;"></div>
      </div>
      <div style="display: flex;margin-top: 10px;flex-direction: column;">
        <div style="margin: 20px;">
          当前位置：{{ address }}
        </div>
        <el-button type="primary" @click="signIn" style="margin: 20px;">确 定</el-button>
        <el-button type="primary" @click="initAMap" style="margin: 20px;">刷新位置</el-button>
      </div>
    </div>
  </template>
  
  <script>
  import AMapLoader from "@amap/amap-jsapi-loader";
  import request from "@/utils/request";
  
  export default {
    name: "LocationGet",
    data() {
      return {
        map: null,
        address: "",
        keyword: "",
      };
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
          plugins: ["AMap.Scale","AMap.Geocoder","AMap.Autocomplete","AMap.PlaceSearch"], // 需要使用的的插件列表，如比例尺'AMap.Scale'，支持添加多个如：['...','...']
        })
          .then((AMap) => {
            // 获取用户位置并设置地图中心点
            navigator.geolocation.getCurrentPosition(
              (position) => {
                const { latitude, longitude } = position.coords;
                this.map = new AMap.Map("container", {
                  // 设置地图容器id
                  viewMode: "3D", // 是否为3D地图模式
                  zoom: 16, // 初始化地图级别
                  center: [longitude, latitude], // 将用户位置设为地图中心点
                });

                this.map.on('click', (e) => {
                  // 获取点击位置的经纬度坐标
                  const lnglat = e.lnglat;

                  // 创建逆地理编码对象
                  const geocoder = new AMap.Geocoder({
                      // 可选参数设置
                  });

                  // 逆地理编码，将经纬度坐标转换为地址信息
                  geocoder.getAddress(lnglat, (status, result) => {
                      if (status === 'complete' && result.info === 'OK') {
                          // 获取地址信息
                          let address = result.regeocode.formattedAddress;
                          this.address = address;
                      } else {
                          console.error('逆地理编码失败：', result);
                      }
                  });
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
  
                  const autoOptions = {
                    input: "tipinput"
                  };
                  const auto = new AMap.Autocomplete(autoOptions);
                  const placeSearch = new AMap.PlaceSearch({
                    map: this.map
                  });

                  AMap.event.addListener(auto, "select", (e) => {
                    placeSearch.setCity(e.poi.adcode);
                    placeSearch.search(e.poi.name);

                    // 将地图中心点移动到选中的位置
                    this.map.setCenter(e.poi.location);
                    // 在地图上添加标记
                    new AMap.Marker({
                      position: e.poi.location,
                      map: this.map
                    });
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
        // 获取存储在LocalStorage中的表单数据
        let storedFormData = localStorage.getItem('form');
        // 如果LocalStorage中存在表单数据，则解析JSON字符串为对象，否则创建一个空对象
        let formData = storedFormData ? JSON.parse(storedFormData) : {};
        // 修改表单数据
        formData.address = this.address;

        request.post(`/users/old`,formData)
          .then(response => {
          if (response.code === 1) {
            console.log(formData);
            // 删除LocalStorage中名为'userData'的数据
            localStorage.removeItem('form');
            this.$emit('next');
            this.$message({
                message: 'success',
                type: 'success'
            });
            this.$router.push('/endAddActivity');
          } else {
            this.$message.error(response.msg);
          }
          })
          .catch(error => {
            console.error('获取数据失败:', error);
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
  