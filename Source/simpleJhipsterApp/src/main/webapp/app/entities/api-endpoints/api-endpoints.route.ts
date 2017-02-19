import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ApiEndpointsComponent } from './api-endpoints.component';
import { ApiEndpointsDetailComponent } from './api-endpoints-detail.component';
import { ApiEndpointsPopupComponent } from './api-endpoints-dialog.component';
import { ApiEndpointsDeletePopupComponent } from './api-endpoints-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ApiEndpointsResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      let page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      let sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
      return {
          page: this.paginationUtil.parsePage(page),
          predicate: this.paginationUtil.parsePredicate(sort),
          ascending: this.paginationUtil.parseAscending(sort)
    };
  }
}

export const apiEndpointsRoute: Routes = [
  {
    path: 'api-endpoints',
    component: ApiEndpointsComponent,
    resolve: {
      'pagingParams': ApiEndpointsResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'simpleJhipsterApp.apiEndpoints.home.title'
    }
  }, {
    path: 'api-endpoints/:id',
    component: ApiEndpointsDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'simpleJhipsterApp.apiEndpoints.home.title'
    }
  }
];

export const apiEndpointsPopupRoute: Routes = [
  {
    path: 'api-endpoints-new',
    component: ApiEndpointsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'simpleJhipsterApp.apiEndpoints.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'api-endpoints/:id/edit',
    component: ApiEndpointsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'simpleJhipsterApp.apiEndpoints.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'api-endpoints/:id/delete',
    component: ApiEndpointsDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'simpleJhipsterApp.apiEndpoints.home.title'
    },
    outlet: 'popup'
  }
];
