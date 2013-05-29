# EHRI Admin Tutorial - part 1

## Introduction

For this tutorial we going to follow a case study of sorts, with two parts:

*Part 1*

> Allow a given user to create/update/delete both *repositories* and their *archival units*, but only for a particular country (the UK)

*[Part 2](tutorial_pt2.md)*

> Create a repository for King's College London (KCL) and add the Hans Adler collection as an archival unit

#### Part 1 - Creating a group with the desired permissions, and adding a user

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

Since we want to create, update and delete items for both repositories and documentary units, check those boxes. For the moment, *ignore the owner and grant* permissions, but *do check* the "annotate" permission, since we want the users in this group to be able to link items.

![Any check the desired permissions that apply. Ignore Owner and Grant permissions for now](09_set_country_permissions_small.png)

Now we're going to create a user and add them to the "UK Archives Subcontractors" group. First, go to the Users menu (under "More...").

![To create a user, first select the More -> Users menu item](10_create_user_small.png)

Then click the "Create New User" button on the right hand side.

![The click the 'Create New User' button](11_create_user_small.png)

Fill out the user's details, such as their email address, identifier, and full name. For their identifier, just use their first name, lowercase. If the identifier already exists the system will complain and make you choose a new one. If this is a real case, use a completely random, strong password.

![Fill out the desired user properties and click 'Create User'](12_create_user_small.png)

Once the user has been created, sign out of your admin account. The next part of this tutorial will be conducted _as the new user_.

![Then sign out of the admin account](13_create_user_sign_out_small.png)

Next, go to [part 2 - Creating a Repository and a Collection](tutorial_pt2.md).
