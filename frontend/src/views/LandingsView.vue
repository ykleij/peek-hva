<template>
  <div class="grid md:grid-cols-3 md:h-screen">
    <LoginComponent class="md:h-full relative border-b md:border-none border-neutral-400 py-10 md:p-0 md:flex md:col-span-1 md:mt-0"/>
    <LandingsPageComponent class="overflow-visible h-fit md:h-full md:col-span-2"/>
  </div>
</template>

<script>
// @ is an alias to /src
import LoginComponent from "@/components/LoginComponent";
import LandingsPageComponent from "@/components/LandingsPageComponent";

export default {
  name: 'LandingsView',
  components: {
    LoginComponent,
    LandingsPageComponent
  }
}
</script>
