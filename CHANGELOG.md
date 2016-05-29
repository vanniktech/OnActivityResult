# Change Log

Version 0.5.0 *(2016-05-29)*
--------------------------------

- @Extra String defaults value should be null and not empty string. Fix \#46 [\#64](https://github.com/vanniktech/OnActivityResult/pull/64) ([vanniktech](https://github.com/vanniktech))
- Add Parcelable support for @Extra [\#60](https://github.com/vanniktech/OnActivityResult/pull/60) ([vanniktech](https://github.com/vanniktech))
- Add test to ensure upper package names are allowed [\#57](https://github.com/vanniktech/OnActivityResult/pull/57) ([vanniktech](https://github.com/vanniktech))
- Add @Extra support for classes implementing Serializable [\#56](https://github.com/vanniktech/OnActivityResult/pull/56) ([vanniktech](https://github.com/vanniktech))
- Tweak proguard rules and add test [\#52](https://github.com/vanniktech/OnActivityResult/pull/52) ([vanniktech](https://github.com/vanniktech))
- Add Serializable support [\#43](https://github.com/vanniktech/OnActivityResult/pull/43) ([vanniktech](https://github.com/vanniktech))
- Add Bundle support [\#42](https://github.com/vanniktech/OnActivityResult/pull/42) ([vanniktech](https://github.com/vanniktech))
- Add CharSequence support [\#41](https://github.com/vanniktech/OnActivityResult/pull/41) ([vanniktech](https://github.com/vanniktech))

Version 0.4.0 *(2016-03-29)*
--------------------------------

- Cache OnActivityResult Classes to use only one reflection call per class [\#39](https://github.com/vanniktech/OnActivityResult/pull/39) ([vanniktech](https://github.com/vanniktech))
- Fix bug when only parent class had @OnActivityResult annotation [\#37](https://github.com/vanniktech/OnActivityResult/pull/37) ([vanniktech](https://github.com/vanniktech))
- Avoid setting didHandle when it's not needed [\#32](https://github.com/vanniktech/OnActivityResult/pull/32) ([vanniktech](https://github.com/vanniktech))
- OnActivityResult now returns boolean whether things were handled or not [\#31](https://github.com/vanniktech/OnActivityResult/pull/31) ([vanniktech](https://github.com/vanniktech))

Version 0.3.0 *(2016-02-27)*
--------------------------------

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