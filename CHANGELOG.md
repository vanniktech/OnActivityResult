# Change Log

Version 0.8.0 *(In development)*
--------------------------------

Version 0.7.0 *(2017-12-08)*
----------------------------

- Update dependencies to latest and clean build files up. [\#100](https://github.com/vanniktech/OnActivityResult/pull/100) ([vanniktech](https://github.com/vanniktech))
- Remove SuperficialValidation.validateElement checks. [\#99](https://github.com/vanniktech/OnActivityResult/pull/99) ([vanniktech](https://github.com/vanniktech))
- Update license in travis.yml file. [\#98](https://github.com/vanniktech/OnActivityResult/pull/98) ([vanniktech](https://github.com/vanniktech))
- Don't clean build again when deploying SNAPSHOTS. [\#94](https://github.com/vanniktech/OnActivityResult/pull/94) ([vanniktech](https://github.com/vanniktech))
- Better Travis Configuration + dependency updates. [\#93](https://github.com/vanniktech/OnActivityResult/pull/93) ([vanniktech](https://github.com/vanniktech))
- Delete codecov yml file. [\#92](https://github.com/vanniktech/OnActivityResult/pull/92) ([vanniktech](https://github.com/vanniktech))
- Don't generate BuildConfig file for release builds. [\#90](https://github.com/vanniktech/OnActivityResult/pull/90) ([vanniktech](https://github.com/vanniktech))
- Use getClassLoader\(\).loadClass\(\) instead of Class.forName [\#89](https://github.com/vanniktech/OnActivityResult/pull/89) ([vanniktech](https://github.com/vanniktech))
- Change AutoService to CompileOnly. [\#88](https://github.com/vanniktech/OnActivityResult/pull/88) ([vanniktech](https://github.com/vanniktech))
- Update Gradle to 3.4 [\#87](https://github.com/vanniktech/OnActivityResult/pull/87) ([vanniktech](https://github.com/vanniktech))
- Support user defined result code [\#86](https://github.com/vanniktech/OnActivityResult/pull/86) ([operando](https://github.com/operando))
- Force Error Prone Version to 2.0.15 [\#85](https://github.com/vanniktech/OnActivityResult/pull/85) ([vanniktech](https://github.com/vanniktech))
- Do not claim annotations in processor. [\#84](https://github.com/vanniktech/OnActivityResult/pull/84) ([vanniktech](https://github.com/vanniktech))
- Update deps to latest [\#83](https://github.com/vanniktech/OnActivityResult/pull/83) ([vanniktech](https://github.com/vanniktech))
- Update all deps [\#81](https://github.com/vanniktech/OnActivityResult/pull/81) ([vanniktech](https://github.com/vanniktech))
- Update Gradle Version Plugin to 0.13.0 [\#79](https://github.com/vanniktech/OnActivityResult/pull/79) ([vanniktech](https://github.com/vanniktech))
- Update Robolectric to 3.1.2 [\#78](https://github.com/vanniktech/OnActivityResult/pull/78) ([vanniktech](https://github.com/vanniktech))
- Update ButterKnife to 8.2.1 [\#77](https://github.com/vanniktech/OnActivityResult/pull/77) ([vanniktech](https://github.com/vanniktech))
- Update Code Quality Tools to 0.4.0 [\#76](https://github.com/vanniktech/OnActivityResult/pull/76) ([vanniktech](https://github.com/vanniktech))
- Remove test prefix [\#73](https://github.com/vanniktech/OnActivityResult/pull/73) ([vanniktech](https://github.com/vanniktech))
- Add @Generated annotation onto generated classes [\#72](https://github.com/vanniktech/OnActivityResult/pull/72) ([vanniktech](https://github.com/vanniktech))
- Update to Robolectric 3.1 [\#71](https://github.com/vanniktech/OnActivityResult/pull/71) ([vanniktech](https://github.com/vanniktech))

Version 0.6.0 *(2016-06-08)*
----------------------------

- Fix Proguard rules [\#70](https://github.com/vanniktech/OnActivityResult/pull/70) ([vanniktech](https://github.com/vanniktech))
- Verify that Numeric Intent names also do work [\#68](https://github.com/vanniktech/OnActivityResult/pull/68) ([vanniktech](https://github.com/vanniktech))
- Generate better names using NameAllocator [\#67](https://github.com/vanniktech/OnActivityResult/pull/67) ([vanniktech](https://github.com/vanniktech))
- Add name\(\) method to all Extra annotations to specify Intent name. Fixes \#47 [\#66](https://github.com/vanniktech/OnActivityResult/pull/66) ([vanniktech](https://github.com/vanniktech))

Version 0.5.0 *(2016-05-29)*
----------------------------

- @Extra String defaults value should be null and not empty string. Fix \#46 [\#64](https://github.com/vanniktech/OnActivityResult/pull/64) ([vanniktech](https://github.com/vanniktech))
- Add Parcelable support for @Extra [\#60](https://github.com/vanniktech/OnActivityResult/pull/60) ([vanniktech](https://github.com/vanniktech))
- Add test to ensure upper package names are allowed [\#57](https://github.com/vanniktech/OnActivityResult/pull/57) ([vanniktech](https://github.com/vanniktech))
- Add @Extra support for classes implementing Serializable [\#56](https://github.com/vanniktech/OnActivityResult/pull/56) ([vanniktech](https://github.com/vanniktech))
- Tweak proguard rules and add test [\#52](https://github.com/vanniktech/OnActivityResult/pull/52) ([vanniktech](https://github.com/vanniktech))
- Add Serializable support [\#43](https://github.com/vanniktech/OnActivityResult/pull/43) ([vanniktech](https://github.com/vanniktech))
- Add Bundle support [\#42](https://github.com/vanniktech/OnActivityResult/pull/42) ([vanniktech](https://github.com/vanniktech))
- Add CharSequence support [\#41](https://github.com/vanniktech/OnActivityResult/pull/41) ([vanniktech](https://github.com/vanniktech))

Version 0.4.0 *(2016-03-29)*
----------------------------

- Cache OnActivityResult Classes to use only one reflection call per class [\#39](https://github.com/vanniktech/OnActivityResult/pull/39) ([vanniktech](https://github.com/vanniktech))
- Fix bug when only parent class had @OnActivityResult annotation [\#37](https://github.com/vanniktech/OnActivityResult/pull/37) ([vanniktech](https://github.com/vanniktech))
- Avoid setting didHandle when it's not needed [\#32](https://github.com/vanniktech/OnActivityResult/pull/32) ([vanniktech](https://github.com/vanniktech))
- OnActivityResult now returns boolean whether things were handled or not [\#31](https://github.com/vanniktech/OnActivityResult/pull/31) ([vanniktech](https://github.com/vanniktech))

Version 0.3.0 *(2016-02-27)*
----------------------------

- Add @Extra annotation [\#26](https://github.com/vanniktech/OnActivityResult/pull/26) ([vanniktech](https://github.com/vanniktech))
- Skip Java Lang imports [\#25](https://github.com/vanniktech/OnActivityResult/pull/25) ([vanniktech](https://github.com/vanniktech))
- Add defaultValue annotation to every @Extra annotation [\#22](https://github.com/vanniktech/OnActivityResult/pull/22) ([vanniktech](https://github.com/vanniktech))
- Add Extra annotation for primitive types and String [\#21](https://github.com/vanniktech/OnActivityResult/pull/21) ([vanniktech](https://github.com/vanniktech))
- Add custom error messages when class with annotated methods is private [\#19](https://github.com/vanniktech/OnActivityResult/pull/19) ([vanniktech](https://github.com/vanniktech))

Version 0.2.0 *(2016-01-18)*
----------------------------

- Add @NonNull and @Nullable support to @IntentData parameters [\#13](https://github.com/vanniktech/OnActivityResult/pull/13) ([vanniktech](https://github.com/vanniktech))
- Add resultCodes\(\) function to @OnActivityResult annotation [\#10](https://github.com/vanniktech/OnActivityResult/pull/10) ([vanniktech](https://github.com/vanniktech))
- Add @IntentData annotation [\#4](https://github.com/vanniktech/OnActivityResult/pull/4) ([vanniktech](https://github.com/vanniktech))

Version 0.1.0 *(2015-12-19)*
----------------------------

- Initial release