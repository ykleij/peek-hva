import { RestAdaptor } from '@/services/RestAdaptor';

export class ProjectRoleService {
  resourcesUrl;
  sectors;
  roles;
  tools;

  constructor(resourcesUrl) {
    this.resourcesUrl = resourcesUrl;
    this.sectors = [];
    this.roles = [];
    this.tools = [];
  }

  async getAllSectors() {
    try {
      const response = await RestAdaptor.fetchJson(
        this.resourcesUrl + '/sectors',
        {
          method: 'GET',
        }
      );
      return (this.sectors = response);
    } catch (e) {
      console.log('Could not find sectors', e);
      return [];
    }
  }

  async getAllRoles() {
    try {
      const response = await RestAdaptor.fetchJson(
        this.resourcesUrl + '/roles',
        {
          method: 'GET',
        }
      );
      return (this.roles = response);
    } catch (e) {
      console.log('Could not find roles', e);
      return [];
    }
  }

  async getAllTools() {
    try {
      const response = await RestAdaptor.fetchJson(
        this.resourcesUrl + '/tools',
        {
          method: 'GET',
        }
      );
      return (this.tools = response);
    } catch (e) {
      console.log('Could not find tools', e);
      return [];
    }
  }

  async getRolesByUserId(id) {
    const response = await RestAdaptor.fetchJson(
      this.resourcesUrl + '/roles' + `/${id}`,
      {
        method: 'GET',
      }
    );
    console.log(response);

    return this.roles;
  }

  async updateUserRoles(roles, userId) {
    const url = `${this.resourcesUrl}/roles/${userId}`;

    try {
      await RestAdaptor.fetchJson(url, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(roles),
      });

      this.roles.push(roles);
    } catch (error) {
      console.error('Error saving notification:', error.message);
    }
  }

  async createProjectRole(data) {
    const url = `${this.resourcesUrl}/create`;

    try {
      await RestAdaptor.fetchJson(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
      });
    } catch (error) {
      console.log(error);
    }
  }

  async editProjectRole(data) {
    const url = `${this.resourcesUrl}/update`;
    try {
      await RestAdaptor.fetchJson(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
      });
    } catch (error) {
      console.log(error);
    }
  }
}
