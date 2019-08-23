SUMMARY = "SDK recipe for an image with most of the non-graphical \
libraries and development tools."

require recipes-extended/images/core-image-lsb-sdk.bb

include core-image-full.inc

TOOLCHAIN_HOST_TASK =+ " nativesdk-python-modules nativesdk-python3-modules \
nativesdk-python-six nativesdk-e2fsprogs"

SDK_INHERIT_BLACKLIST =+ " buildhistory icecc cve-check buildhistory-extra \
buildstats-summary"
