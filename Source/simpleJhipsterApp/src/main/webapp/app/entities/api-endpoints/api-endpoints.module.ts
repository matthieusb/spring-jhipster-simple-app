import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SimpleJhipsterAppSharedModule } from '../../shared';

import {
    ApiEndpointsService,
    ApiEndpointsPopupService,
    ApiEndpointsComponent,
    ApiEndpointsDetailComponent,
    ApiEndpointsDialogComponent,
    ApiEndpointsPopupComponent,
    ApiEndpointsDeletePopupComponent,
    ApiEndpointsDeleteDialogComponent,
    apiEndpointsRoute,
    apiEndpointsPopupRoute,
    ApiEndpointsResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...apiEndpointsRoute,
    ...apiEndpointsPopupRoute,
];

@NgModule({
    imports: [
        SimpleJhipsterAppSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ApiEndpointsComponent,
        ApiEndpointsDetailComponent,
        ApiEndpointsDialogComponent,
        ApiEndpointsDeleteDialogComponent,
        ApiEndpointsPopupComponent,
        ApiEndpointsDeletePopupComponent,
    ],
    entryComponents: [
        ApiEndpointsComponent,
        ApiEndpointsDialogComponent,
        ApiEndpointsPopupComponent,
        ApiEndpointsDeleteDialogComponent,
        ApiEndpointsDeletePopupComponent,
    ],
    providers: [
        ApiEndpointsService,
        ApiEndpointsPopupService,
        ApiEndpointsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SimpleJhipsterAppApiEndpointsModule {}
