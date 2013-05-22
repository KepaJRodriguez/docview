# EHRI Admin Tutorial

## Introduction

For this tutorial we going to follow a case study of sorts, with two parts:

#### Part 1

> Allow a given user to create/update/delete both *repositories* and their *archival units*, but only for a particular country (the UK)

#### Part 2

> Create a repository for King's College London (KCL) and add the Hans Adler collection as an archival unit

## Part 1 - Creating a group with the desired permissions, and adding a user

First, we need to create a new group. To do this, first go to the groups page (located under the 'More...' menu.)

![To create a group, first go to the More -> Groups menu ite](01_create_group_small.png)

Then, click the "Create New Group" button.

![Then, click on the 'Create New Group' button](02_create_group_small.png)

Fill out the identifier, name, and description sections in the group form. Currently, the identifier is fairly arbitrary: if in doubt, use a lowercase version of the group name. For the group name, pick something quite descriptive, and then expand on that in the description field, saying what the group is supposed to do.

In this example we'll use the following as the name: "UK Archives Subcontractors"

![Fill out the desired group properties](03_create_group_small.png)

Now we need to add permissions for our new "UK Archives Subcontractors" group to manage repositories and documentary units within the UK (and no-where else.)  For the moment, this is a little unintuitive, since we need go to the country in question and add "Scoped" permissions for our new group.

First, go to the "Countries" menu located under "More...".

![Countries are accessed through the More -> Countries menu item](04_set_country_permissions_small.png)

Then, locate the country we want via the search function and select it.

![Search for the country you want and then click on it](05_set_country_permissions_small.png)

Now click the "Manage Permissions" link on the "United Kingdom" page.

![To give a group permissions within a country, click the 'Manage Permissions' item](06_set_country_permissions_small.png)

There are two types of permissions one can manage from this page: item level and scoped. Item level permissions refer to the item itself, in this case the data for the UK country record. Scoped permissions refer to items "inside" the UK, such as repositories, and the documents those repositories contain.

We want to set the latter kind (scoped) so click the "Scoped Permissions" link.

![Then click the 'Scoped Permissions' link](07_set_country_permissions_small.png)

Now we need to choose the group we're setting permissions for. Click our "UK Archives Subcontractors" group in the list of groups.

![Select the group we want to operate on](08_set_country_permissions_small.png)



![Any check the desired permissions that apply. Ignore Owner and Grant permissions for now](09_set_country_permissions_small.png)
![To create a user, first select the More -> Users menu item](10_create_user_small.png)
![The click the 'Create New User' button](11_create_user_small.png)
![Fill out the desired user properties and click 'Create User'](12_create_user_small.png)
![Then sign out of the admin account](13_create_user_sign_out_small.png)

## Part 2 - As the new user, create a new repository and add an archival unit

![To create a repository, first find the country it is based in. Access countries through the More -> Countries menu item](14_create_repo_small.png)
![Then search for the country we want](15_create_repo_small.png)
![From the country's page, click the 'Create Repository' button](16_create_repo_small.png)
![Fill out the relevant ISDIAH properties for the new repository and click 'Create Repository'](17_create_repo_small.png)
![To create a new collection, go to its holding repository and click 'Create New Collection'](18_create_collection_small.png)
![Fill out the collection's ISAD(G) properties and click 'Create Collection'](19_create_collection_small.png)
![To manage access points, go to the Actions downdown and click 'Manage Access Points'](20_create_collection_manage_access_small.png)
![Click the 'Add New' button on the desired section and start typing the access point's name. If an existing item appears, click on it](21_create_collection_manage_access_small.png)
![Then click the 'Save' button](22_create_collection_manage_access_small.png)
![Repeat for other access points, which may or may not be in the database](23_create_collection_manage_access_small.png)
