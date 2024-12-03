import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import { Partner } from '../models/partner.model';
import { PartnerService } from '../services/partner.service';
import { PartnerDialogComponent } from './partner-dialog/partner-dialog.component';

@Component({
  selector: 'app-partners',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './partners.component.html',
  styleUrls: ['./partners.component.scss']
})
export class PartnersComponent implements OnInit {
  partners: Partner[] = [];
  displayedColumns = ['alias', 'type', 'direction', 'processedFlowType', 'description', 'actions'];

  constructor(
    private partnerService: PartnerService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.loadPartners();
  }

  loadPartners() {
    this.partnerService.getAll().subscribe(data => {
      this.partners = data;
    });
  }

  openDialog(partner?: Partner) {
    const dialogRef = this.dialog.open(PartnerDialogComponent, {
      width: '500px',
      data: partner || {}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        if (result.id) {
          this.partnerService.update(result.id, result).subscribe(() => {
            this.loadPartners();
          });
        } else {
          this.partnerService.create(result).subscribe(() => {
            this.loadPartners();
          });
        }
      }
    });
  }

  deletePartner(id: number) {
    if (confirm('Are you sure you want to delete this partner?')) {
      this.partnerService.delete(id).subscribe(() => {
        this.loadPartners();
      });
    }
  }
}
