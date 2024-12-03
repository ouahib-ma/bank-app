export const environment = {
  production: false,
  appName: 'Bank App',
  version: '1.0.0',
  apiMessageService: {
    baseUrl: 'http://localhost:8081/api',
    endpoints: '/messages'
  },
  apiPartenaireService: {
    baseUrl: 'http://localhost:8083/api',
    endpoints: '/partenaires'
  },
};
