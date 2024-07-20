<template>
  <Bar v-if="this.loaded === true" :data="chartData" :options="chartOptions"/>
</template>

<script>
import {Bar} from 'vue-chartjs'
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
} from 'chart.js'


ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

export default {
  name: 'UserChart',
  inject: {
    usersService: {from: 'usersService'},
    usersYesterdayService: {from: "usersYesterdayService"},
  },
  async mounted() {
    try {
      this.loaded = false
      //checking for cache
      if (this.usersService.entities.length > 0) {
        this.usersAmountToday = this.usersService.entities.length;
      } else {
        await this.usersService.asyncFindAll();
        this.usersAmountToday = this.usersService.entities.length;
      }
      //is to be loaded from backend, because is not loaded anywhere else
      await this.usersYesterdayService.getUsersFromYesterday();
      this.usersAmountYesterday = this.usersYesterdayService.entities.length;
      this.chartData.datasets[0].data = [this.usersAmountYesterday, this.usersAmountToday];
      this.loaded = true

    } catch (e) {
      console.error(e)
    }
  },
  components: {Bar},
  data() {
    return {
      loaded: false,
      usersAmountToday: null,
      usersAmountYesterday: null,
      chartData: {
        labels: ['Yesterday', 'Today'],
        datasets: [{
          label: 'Users',
          backgroundColor: '#373737',
          data: [null, null]
        }]
      },
      chartOptions: {
        responsive: true,
        scales: {
          x: {
            ticks: {
              font: {
                size: 22
              }
            }
          },
          y: {
            beginAtZero: true,
            ticks: {
              font: {
                size: 22
              }
            },
          }
        },
        plugins: {
          legend: {
            labels: {
              font: {
                size: 22, // Set the font size for the legend
              }
            }
          }
        }
      }
    }
  }
}
</script>