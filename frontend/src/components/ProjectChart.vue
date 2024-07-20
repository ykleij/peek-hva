<template>
  <Bar v-if="loaded" :data="chartData" :options="chartOptions"/>
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
  name: 'ProjectChart',
  inject: {
    projectsService: {from: 'projectsService'},
    projectYesterdayService: {from: "projectYesterdayService"}
  },
  async mounted() {
    this.loaded = false
    try {
      //checking for cache
      if (this.projectsService.entities.length > 0) {
        this.projectAmountToday = this.projectsService.entities.slice().filter((project) => project.status === 1).length;
      } else {
        await this.projectsService.asyncFindAll();
        this.projectAmountToday = this.projectsService.entities.slice().filter((project) => project.status === 1).length;
      }
      //is to be loaded from backend, because is not loaded anywhere else
      await this.projectYesterdayService.getProjectsFromYesterday();
      this.projectAmountYesterday = this.projectYesterdayService.entities.slice().filter((project) => project.status === 1).length;
      //showing only active projects
      this.chartData.datasets[0].data = [this.projectAmountYesterday, this.projectAmountToday];
      this.loaded = true
    } catch (e) {
      console.error(e)
    }
  },
  components: {Bar},
  data() {
    return {
      loaded: false,
      projectAmountToday: null,
      projectAmountYesterday: null,
      chartData: {
        labels: ['Yesterday', 'Today'],
        datasets: [{
          label: 'Projects',
          backgroundColor: '#41424c',
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