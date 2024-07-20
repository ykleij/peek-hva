export class Invitation {
  constructor(id = 0, eventName, maxPeople, url) {
    this.id = id;
    this.eventName = eventName;
    this.maxPeople = maxPeople;
    this.url = url;
  }

  static copyConstructor(project) {
    if (project == null) {
      return null;
    }
    return Object.assign(new Invitation(), project);
  }
}
