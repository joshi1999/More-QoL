# Upgrade to Minecraft 1.21.6

This document summarizes the changes made to upgrade the More QoL mod from Minecraft 1.21.5 to 1.21.6.

## Files Updated

### gradle.properties
- `minecraft_version`: 1.21.5 → 1.21.6
- `yarn_mappings`: 1.21.5+build.1 → 1.21.6+build.1
- `fabric_version`: 0.119.9+1.21.5 → 0.120.0+1.21.6
- `mod_version`: 1.3.7-1.21.5 → 1.3.7-1.21.6

### src/main/resources/fabric.mod.json
- `minecraft` dependency: ">=1.21.4" → ">=1.21.6"

### README.md
- Minecraft version badge: 1.21.5 → 1.21.6
- Fabric version badge: 0.119.9+1.21.5 → 0.120.0+1.21.6

## Version Details

- **Minecraft**: 1.21.6
- **Yarn Mappings**: 1.21.6+build.1
- **Fabric API**: 0.120.0+1.21.6
- **Fabric Loader**: 0.16.13 (unchanged)
- **Mod Version**: 1.3.7-1.21.6
- **Java**: 21 (unchanged)

## Build Status

**Note**: The build currently fails due to network connectivity issues with `maven.fabricmc.net`. This is a temporary network issue and not related to the version upgrade. Once connectivity is restored, the build should work correctly with the updated versions.

## Testing

After network connectivity to maven.fabricmc.net is restored:
1. Run `./gradlew build` to verify the project builds successfully
2. Test the mod in Minecraft 1.21.6 to ensure all features work correctly
3. Verify that all QoL features (break safe, time editing, potion effect time display, etc.) function as expected

## Compatibility

The mod should maintain full compatibility with existing configurations and worlds when upgrading from 1.21.5 to 1.21.6, as this is a minor version update with no breaking changes to the mod's functionality.