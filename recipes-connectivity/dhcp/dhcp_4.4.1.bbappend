FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

# These patch files are from fedora package dhcp-4.4.1-12.fc31.src.rpm and here
# used here for IPoIB support (primarily).  It would be great if ISC could take
# IB support but I guess that's never going to happen?

SRC_URI_append = "file://0001-change-bug-url.patch \
                  file://0002-additional-dhclient-options.patch \
                  file://0003-Handle-releasing-interfaces-requested-by-sbin-ifup.patch \
                  file://0004-Support-unicast-BOOTP-for-IBM-pSeries-systems-and-ma.patch \
                  file://0005-Change-default-requested-options.patch \
                  file://0006-Various-man-page-only-fixes.patch \
                  file://0007-Change-paths-to-conform-to-our-standards.patch \
                  file://0008-Make-sure-all-open-file-descriptors-are-closed-on-ex.patch \
                  file://0009-Fix-garbage-in-format-string-error.patch \
                  file://0010-Handle-null-timeout.patch \
                  file://0011-Drop-unnecessary-capabilities.patch \
                  file://0012-RFC-3442-Classless-Static-Route-Option-for-DHCPv4-51.patch \
                  file://0013-DHCPv6-over-PPP-support-626514.patch \
                  file://0014-IPoIB-support-660681.patch \
                  file://0015-Add-GUID-DUID-to-dhcpd-logs-1064416.patch \
                  file://0016-Turn-on-creating-sending-of-DUID.patch \
                  file://0017-Send-unicast-request-release-via-correct-interface.patch \
                  file://0018-No-subnet-declaration-for-iface-should-be-info-not-e.patch \
                  file://0019-dhclient-write-DUID_LLT-even-in-stateless-mode-11563.patch \
                  file://0020-Discover-all-hwaddress-for-xid-uniqueness.patch \
                  file://0021-Load-leases-DB-in-non-replay-mode-only.patch \
                  file://0022-Backport-sd-notify-patch-for-systemd-support-1687040.patch \
                  "
