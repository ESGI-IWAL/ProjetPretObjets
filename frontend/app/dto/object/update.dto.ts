import type { EObjectCategories } from "~/enums/object/categories.enum";
import type { EObjectMaterial } from "~/enums/object/material.enum";
import type { EObjectState } from "~/enums/object/state.enum";

export interface IUpdateObjectDto {
    id: number;
    images?: string[];
    name?: string;
    description?: string;
    category?: EObjectCategories;
    weight?: number;
    dimensions?: string;
    state?: EObjectState;
    material?: EObjectMaterial;
}