# Process Instance Migration Examples 

## Example 1: Migration example with activity match
The following example describes a scenario where a one-to-one mapping of the activities within the source process definition to the activities within the target process definition exist. In other words, defining a one-to-one relation instruction means that an instance of the source activity is migrated into an instance of the target activity. 

* [Example Code](example1-migration-with-activity-match/README.md)

## Example 2: Migration example with activity mismatch
The following example describes a scenario where there is not a one-to-one mapping of the activities within the source process definition to the activities within the target process definition exist. An activity within the source process definition was removed in the target definition and an explicit mapping is required to map the activity instances to an alternative activity within the target process definition.

* [Example Code](example2-migration-with-activity-mismatch/README.md)

## Example 3: Migration example with data consistency
The following example describes a scenario where there is not a one-to-one mapping of the activities within the source process definition to the activities within the target process definition exist. In addition to the activity mismatch, there is also a data consistency issue.

* [Example Code](example3-migration-with-data-consistency/README.md)

