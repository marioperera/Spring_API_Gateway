import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { PublishNewApiComponent } from './components/publish-new-api/publish-new-api.component';
import {  BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatTabsModule} from '@angular/material/tabs';
import {MatFormFieldModule} from '@angular/material/form-field';
import { SearchapiComponent } from './components/searchapi/searchapi.component';
import { CreatenewapiComponent } from './components/createnewapi/createnewapi.component'; 
import {MatInputModule} from '@angular/material';
import {MatCheckboxModule} from '@angular/material/checkbox'; 
import {MatCardModule} from '@angular/material/card'; 
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { from } from 'rxjs';

@NgModule({
  declarations: [
    AppComponent,
    PublishNewApiComponent,
    SearchapiComponent,
    CreatenewapiComponent
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    BrowserModule,
    AppRoutingModule,
    NoopAnimationsModule,
    BrowserAnimationsModule,
    MatTabsModule,
    MatFormFieldModule,
    MatInputModule,
    MatCheckboxModule,
    MatCardModule,
    HttpClientModule
  ],
  providers: [HttpClientModule],
  bootstrap: [AppComponent]
})
export class AppModule { }
