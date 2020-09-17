SUMMARY = "Linux mainline kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

inherit kernel fsl-kernel-localversion

S = "${WORKDIR}/git"

DEPENDS += "lzop-native bc-native"
RDEPENDS_${PN} += "wireless-regdb"

BRANCH = "linux-5.4.y"
LOCALVERSION = "+dr-1.1"
SRCREV = "6c3d34dea2fc9b02c4419af4e368e8b73f566c88"
SRC_URI = " \
	git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;branch=${BRANCH} \
	file://defconfig \
"

COMPATIBLE_MACHINE = "(vec6200|vec6200-factory)"