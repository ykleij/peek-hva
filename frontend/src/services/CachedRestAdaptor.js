import {RestAdaptor} from "@/services/RestAdaptor";

export class CachedRestAdaptor extends RestAdaptor {
    entities;

    constructor(resourcesUrl, copyConstructor) {
        super(resourcesUrl, copyConstructor);
        this.entities = [];
    }

    async asyncFindAll() {
        this.entities = await super.asyncFindAll();
        return this.entities;
    }

    async asyncFindById(id) {
        const entity = await super.asyncFindById(id);
        try {
            const index = this.entities.findIndex((e) => e.id === id);
            if (index !== -1) {
                this.entities[index] = entity;
            }
        } catch (e) {
            console.log("Could not find entity")
        }

        return entity;
    }

    async asyncSave(entity) {
        const savedEntity = await super.asyncSave(entity);
        const index = this.entities.findIndex((e) => e.id === savedEntity.id);
        if (index !== -1) {
            this.entities[index] = savedEntity;
        } else {
            this.entities.push(savedEntity);
        }
        return savedEntity;
    }

    async asyncDeleteById(id) {
        await super.asyncDeleteById(id);
        this.entities = this.entities.filter((e) => e.id !== id);
    }
}