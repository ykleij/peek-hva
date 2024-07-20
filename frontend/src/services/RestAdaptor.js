// import { errorHandler } from '@/utils/actionHandler';

export class RestAdaptor {
  resourcesUrl;
  copyConstructor;

  constructor(resourcesURL, copyConstructor) {
    this.resourcesUrl = resourcesURL;
    this.copyConstructor = copyConstructor;
  }

  static async fetchJson(url, options = null) {
    try {
      let response = await fetch(url, options);
      if (response.ok) {
        return await response.json();
      } else {
        // await errorHandler('Something went wrong');
        //console.log(response, !response.bodyUsed ? await response.text() : "");
      }
      return response;
    } catch (e) {
      console.log(e);
    }
  }

  async asyncFindAll() {
    const data = await RestAdaptor.fetchJson(this.resourcesUrl, {
      method: 'GET',
    });
    try {
      return data?.map((e) => this.copyConstructor(e));
    } catch (e) {
      return data;
    }
  }

  async asyncFindById(id) {
    return this.copyConstructor(
      await RestAdaptor.fetchJson(`${this.resourcesUrl}/${id}`)
    );
  }

  async asyncSave(entity) {
    // Checks whether a POST or PUT request needs to be done (if the entity has an id, assume it's an update (PUT)).
    const isUpdate = entity.id !== undefined;

    const url = isUpdate
      ? `${this.resourcesUrl}/${entity.id}`
      : this.resourcesUrl;
    const method = isUpdate ? 'PUT' : 'POST';

    const savedEntity = await RestAdaptor.fetchJson(url, {
      method: method,
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(entity),
    });

    return this.copyConstructor(savedEntity);
  }

  async asyncDeleteById(id) {
    await RestAdaptor.fetchJson(this.resourcesUrl + '/' + id, {
      method: 'DELETE',
    });
  }
}
