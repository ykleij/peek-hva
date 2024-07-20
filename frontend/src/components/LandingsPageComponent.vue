<template>
  <div
      class="landingspage md:overflow-y-scroll justify-center bg-gradient-to-b from-white to-neutral-100 scroll-smooth z-40 text-2xl text-neutral-700">
    <nav class="fixed top-10 right-10 flex flex-row gap-5">
      <RouterLink to="/faq" class="text-sm bg-neutral-900 py-2 px-4 z-50 rounded-xl text-neutral-100">FAQ</RouterLink>
      <!--      <RouterLink to="/about" class="text-sm  py-2 px-4 z-50 rounded-full">About</RouterLink>-->
    </nav>
    <div
        class="my-52 md:my-0 md:h-full flex items-center text-start text-4xl">
      <div id="welcome" class="font-semibold text-3xl sm:text-4xl h-12 w-2/3 ml-10">
        Welcome
        <div>
          <span id="text" class="font-normal text-xl md:text-3xl"></span>
          <span id="cursor" class="font-light text-xl md:text-2xl">|</span>
        </div>
      </div>
    </div>

    <!--    <div class="overflow-hidden py-24 sm:py-32">-->
    <!--      <div class="mx-auto max-w-7xl px-6 lg:px-8">-->
    <!--        <div-->
    <!--            class="mx-auto grid max-w-2xl grid-cols-1 gap-x-8 gap-y-16 sm:gap-y-20 lg:mx-0 lg:max-w-none lg:grid-cols-2">-->
    <!--          <div class="lg:pr-8 lg:pt-4">-->
    <!--            <div class="lg:max-w-lg">-->
    <!--              <h2 class="text-base font-semibold leading-7">Deploy faster</h2>-->
    <!--              <p class="mt-2 text-3xl font-bold tracking-tight sm:text-4xl">-->
    <!--                A better workflow-->
    <!--              </p>-->
    <!--              <p class="mt-6 text-lg leading-8">-->
    <!--                Lorem ipsum, dolor sit amet consectetur adipisicing elit.-->
    <!--                Maiores impedit perferendis suscipit eaque, iste dolor-->
    <!--                cupiditate blanditiis ratione.-->
    <!--              </p>-->
    <!--              <dl-->
    <!--                  class="mt-10 max-w-xl space-y-8 text-base leading-7 lg:max-w-none">-->
    <!--                <div-->
    <!--                    v-for="feature in features"-->
    <!--                    :key="feature.name"-->
    <!--                    class="relative pl-9">-->
    <!--                  <dt class="inline font-semibold">-->
    <!--                    <component-->
    <!--                        :is="feature.icon"-->
    <!--                        class="absolute left-1 top-1 h-5 w-5"-->
    <!--                        aria-hidden="true"/>-->
    <!--                    {{ feature.name }}-->
    <!--                  </dt>-->
    <!--                  {{ ' ' }}-->
    <!--                  <dd class="inline">{{ feature.description }}</dd>-->
    <!--                </div>-->
    <!--              </dl>-->
    <!--            </div>-->
    <!--          </div>-->
    <!--          <img-->
    <!--              src="../assets/img/img.png"-->
    <!--              alt="Product screenshot"-->
    <!--              class="w-[48rem] max-w-none rounded-xl shadow-xl ring-1 ring-gray-400/10 sm:w-[57rem] md:-ml-4 lg:-ml-0"-->
    <!--              width="2432"-->
    <!--              height="1442"/>-->
    <!--        </div>-->
    <!--      </div>-->
    <!--    </div>-->

    <div class="py-24 mb-32 px-8 md:px-16">
      <div class="mx-auto max-w-7xl px-6 lg:px-8">
        <div class="mx-auto max-w-2xl text-center space-y-14">
          <!--          <h2 class="text-base font-semibold leading-7">Deploy faster</h2>-->
          <p class="mt-2 text-3xl tracking-tight sm:text-4xl">
            Everything you need to reliably connect with projects and experts
          </p>
<!--          <p class="mt-6 text-lg font-light leading-8">-->
<!--            Discover seamless collaboration on our web application connecting projects and experts reliably. Streamline-->
<!--            your professional journey with an intuitive platform designed for efficient and meaningful connections.-->
<!--          </p>-->
        </div>
        <div class="mx-auto mt-16 max-w-2xl sm:mt-20 lg:mt-24 lg:max-w-4xl">
          <dl
              class="grid max-w-xl grid-cols-1 gap-x-8 gap-y-10 lg:max-w-none lg:grid-cols-2 lg:gap-y-16">
            <div
                v-for="(feature, index) in moreFeatures"
                :key="feature.name"
                class="relative pl-16">
              <dt class="text-base font-semibold leading-7">
                <div
                    class="absolute shadow-lg left-0 top-0 flex h-10 w-10 items-center justify-center rounded-full bg-neutral-800 text-neutral-50">
                  <!--                          <component-->
                  <!--                              :is="feature.icon"-->
                  <!--                              class="h-6 w-6"-->
                  <!--                              aria-hidden="true"/>-->
                  <div>{{ index + 1 }}</div>
                </div>
                {{ feature.name }}
              </dt>
              <dd class="mt-2 text-base leading-7">
                {{ feature.description }}
              </dd>
            </div>
          </dl>
        </div>
      </div>
    </div>

    <!--Footer container-->
    <footer
        class="flex flex-col items-center bg-neutral-800 text-center text-white">
      <div class="container pt-9">
        <div class="mb-9 flex justify-center">
          <a href="https://www.linkedin.com/in/mgtpeek/" class="mr-9 text-neutral-100">
            <svg
                xmlns="http://www.w3.org/2000/svg"
                class="h-4 w-4"
                fill="currentColor"
                viewBox="0 0 24 24">
              <path
                  d="M4.98 3.5c0 1.381-1.11 2.5-2.48 2.5s-2.48-1.119-2.48-2.5c0-1.38 1.11-2.5 2.48-2.5s2.48 1.12 2.48 2.5zm.02 4.5h-5v16h5v-16zm7.982 0h-4.968v16h4.969v-8.399c0-4.67 6.029-5.052 6.029 0v8.399h4.988v-10.131c0-7.88-8.922-7.593-11.018-3.714v-2.155z"/>
            </svg>
          </a>
        </div>
      </div>

      <!--      &lt;!&ndash;Copyright section&ndash;&gt;-->
      <!--      <div-->
      <!--          class="w-full text-sm bg-neutral-800 p-4 text-center text-neutral-200">-->
      <!--        © 2023 Copyright:-->
      <!--        <a class="text-neutral-100">Peek</a>-->
      <!--      </div>-->
    </footer>
  </div>
</template>

<script>
import {
  CloudArrowUpIcon,
  LockClosedIcon,
  ServerIcon,
} from '@heroicons/vue/20/solid';
import {ArrowPathIcon} from '@heroicons/vue/24/outline';
import {gsap} from 'gsap';

export default {
  name: 'LandingsPageComponent',
  created() {
  },
  mounted() {
    const sentences = [
      'to the Peek homepage.',
      'Discover our cutting-edge web application tailored for seamless collaboration.',
      'Embark on a journey with Peek, a platform where seasoned experts and diverse projects converge effortlessly.',
      'We eagerly anticipate the opportunity to engage in fruitful collaboration with you.',
      'For any inquiries, peruse our FAQ section.',
      'Initiate your professional expedition with us.'
    ];

    let tl = gsap.timeline();
    tl.from('#welcome', {duration: 1, y: '7vw', ease: 'power3.out'});

    let tlMain = gsap.timeline({repeat: -1});
    sentences.forEach((sentence) => {
      let tl2 = gsap.timeline({
        repeat: 1,
        yoyo: true,
        repeatDelay: 1,
        delay: 1,
      });
      tl2.to('#text', {duration: 2, text: sentence});
      tlMain.add(tl2);
    });
  },
  data() {
    return {
      open: true,
      moreFeatures: [
        {
          name: 'Create Your Account',
          description:
              'Initiate your journey by joining our platform. Complete your profile with essential details, offering a comprehensive snapshot of your expertise and project preferences.',
          icon: CloudArrowUpIcon,
        },
        {
          name: 'Discover and Engage',
          description:
              'After profile completion, explore a variety of projects and build a personalized list of favorites. While our admin team activates our intelligent matching tool, stay updated on project developments.',
          icon: LockClosedIcon,
        },
        {
          name: 'Connect with other Experts',
          description:
              'Engage with a dynamic community of experts using our intuitive interface. Send and receive collaboration invitations to foster connections that align with your professional goals.',
          icon: ArrowPathIcon,
        }
      ],
      features: [
        {
          name: 'Push to deploy.',
          description:
              'Lorem ipsum, dolor sit amet consectetur adipisicing elit. Maiores impedit perferendis suscipit eaque, iste dolor cupiditate blanditiis ratione.',
          icon: CloudArrowUpIcon,
        },
        {
          name: 'SSL certificates.',
          description:
              'Anim aute id magna aliqua ad ad non deserunt sunt. Qui irure qui lorem cupidatat commodo.',
          icon: LockClosedIcon,
        },
        {
          name: 'Database backups.',
          description:
              'Ac tincidunt sapien vehicula erat auctor pellentesque rhoncus. Et magna sit morbi lobortis.',
          icon: ServerIcon,
        },
      ],
    };
  },
  methods: {},
};
</script>

<style scoped>
.landingspage {
  scrollbar-width: thin;
}

::-webkit-scrollbar {
  width: 5px;
}

::-webkit-scrollbar-track {
  background: #fff;
  border-radius: 8px;
}

::-webkit-scrollbar-track:hover {
  background: #d9d9d9;
}

::-webkit-scrollbar-thumb {
  border-radius: 8px;
  background: #676767;
}

#cursor {
  animation: blink-animation 2s steps(5, start) infinite;
  -webkit-animation: blink-animation 2s steps(5, start) infinite;
}

@keyframes blink-animation {
  to {
    visibility: hidden;
  }
}

@-webkit-keyframes blink-animation {
  to {
    visibility: hidden;
  }
}
</style>
