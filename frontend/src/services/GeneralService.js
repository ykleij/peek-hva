import { RestAdaptor } from '@/services/RestAdaptor';

export class GeneralService {
  resourcesUrl;

  constructor(resourcesUrl) {
    this.resourcesUrl = resourcesUrl;
  }

  async sendInvitation(body) {
    const response = RestAdaptor.fetchJson(
      this.resourcesUrl + '/invitations/send-invitation',
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(body),
      }
    );
    return response;
  }

  async deleteInvitation(invitationId) {
    const response = RestAdaptor.fetchJson(
      this.resourcesUrl + '/invitations/' + invitationId,
      {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
      }
    );
    return response;
  }
}
