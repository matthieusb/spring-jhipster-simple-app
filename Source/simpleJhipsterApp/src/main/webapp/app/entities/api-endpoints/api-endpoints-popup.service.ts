import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ApiEndpoints } from './api-endpoints.model';
import { ApiEndpointsService } from './api-endpoints.service';
@Injectable()
export class ApiEndpointsPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private apiEndpointsService: ApiEndpointsService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.apiEndpointsService.find(id).subscribe(apiEndpoints => {
                this.apiEndpointsModalRef(component, apiEndpoints);
            });
        } else {
            return this.apiEndpointsModalRef(component, new ApiEndpoints());
        }
    }

    apiEndpointsModalRef(component: Component, apiEndpoints: ApiEndpoints): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.apiEndpoints = apiEndpoints;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
