package onem.baymax.pan.server.module.file;

import cn.hutool.core.lang.Assert;
import onem.baymax.pan.core.exception.BPanBusinessException;
import onem.baymax.pan.server.BPanServerLauncher;
import onem.baymax.pan.server.module.file.context.CreateFolderContext;
import onem.baymax.pan.server.module.file.context.DeleteFileContext;
import onem.baymax.pan.server.module.file.context.QueryFileListContext;
import onem.baymax.pan.server.module.file.context.UpdateFilenameContext;
import onem.baymax.pan.server.module.file.enums.DelFlagEnum;
import onem.baymax.pan.server.module.file.service.IUserFileService;
import onem.baymax.pan.server.module.file.vo.BPanUserFileVo;
import onem.baymax.pan.server.module.user.context.UserRegisterContext;
import onem.baymax.pan.server.module.user.service.IUserService;
import onem.baymax.pan.server.module.user.vo.UserInfoVo;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import com.google.common.collect.Lists;

/**
 * FileTest
 *
 * @author hujiabin wrote in 2024/3/19 21:32
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BPanServerLauncher.class)
@Transactional
public class FileTest {

    @Resource
    private IUserFileService userFileService;

    @Resource
    private IUserService userService;

    /**
     * 测试用户查询文件列表成功
     */
    @Test
    public void testQueryUserFileListSuccess() {
        Long userId = register();
        UserInfoVo userInfoVO = info(userId);

        QueryFileListContext context = new QueryFileListContext();
        context.setParentId(userInfoVO.getRootFileId());
        context.setUserId(userId);
        context.setFileTypeArray(null);
        context.setDelFlag(DelFlagEnum.NO.getCode());

        List<BPanUserFileVo> result = userFileService.getFileList(context);
        assert CollectionUtils.isNotEmpty(result);
    }

    /**
     * 测试创建文件夹成功
     */
    @Test
    public void testCreateFolderSuccess() {

        Long userId = register();
        UserInfoVo userInfoVo = info(userId);

        CreateFolderContext context = new CreateFolderContext();
        context.setParentId(userInfoVo.getRootFileId());
        context.setUserId(userId);
        context.setFolderName("folder-name");

        Long fileId = userFileService.createFolder(context);
        Assert.notNull(fileId);
    }

    /**
     * 测试文件重命名失败-文件ID无效
     */
    @Test(expected = BPanBusinessException.class)
    public void testUpdateFilenameFailByWrongFileId() {
        Long userId = register();
        UserInfoVo userInfoVO = info(userId);

        CreateFolderContext context = new CreateFolderContext();
        context.setParentId(userInfoVO.getRootFileId());
        context.setUserId(userId);
        context.setFolderName("folder-name");

        Long fileId = userFileService.createFolder(context);
        Assert.notNull(fileId);

        UpdateFilenameContext updateFilenameContext = new UpdateFilenameContext();
        updateFilenameContext.setFileId(fileId + 1);
        updateFilenameContext.setUserId(userId);
        updateFilenameContext.setNewFilename("folder-name-new");

        userFileService.updateFilename(updateFilenameContext);
    }

    /**
     * 测试当前用户ID无效
     */
    @Test(expected = BPanBusinessException.class)
    public void testUpdateFilenameFailByWrongUserId() {
        Long userId = register();
        UserInfoVo userInfoVO = info(userId);

        CreateFolderContext context = new CreateFolderContext();
        context.setParentId(userInfoVO.getRootFileId());
        context.setUserId(userId);
        context.setFolderName("folder-name");

        Long fileId = userFileService.createFolder(context);
        Assert.notNull(fileId);

        UpdateFilenameContext updateFilenameContext = new UpdateFilenameContext();
        updateFilenameContext.setFileId(fileId);
        updateFilenameContext.setUserId(userId + 1);
        updateFilenameContext.setNewFilename("folder-name-new");

        userFileService.updateFilename(updateFilenameContext);
    }

    /**
     * 测试文件名称重复
     */
    @Test(expected = BPanBusinessException.class)
    public void testUpdateFilenameFailByWrongFilename() {
        Long userId = register();
        UserInfoVo userInfoVO = info(userId);

        CreateFolderContext context = new CreateFolderContext();
        context.setParentId(userInfoVO.getRootFileId());
        context.setUserId(userId);
        context.setFolderName("folder-name");

        Long fileId = userFileService.createFolder(context);
        Assert.notNull(fileId);

        UpdateFilenameContext updateFilenameContext = new UpdateFilenameContext();
        updateFilenameContext.setFileId(fileId);
        updateFilenameContext.setUserId(userId);
        updateFilenameContext.setNewFilename("folder-name");

        userFileService.updateFilename(updateFilenameContext);
    }

    /**
     * 校验文件名称已被占用
     */
    @Test(expected = BPanBusinessException.class)
    public void testUpdateFilenameFailByFilenameUnAvailable() {
        Long userId = register();
        UserInfoVo userInfoVO = info(userId);

        CreateFolderContext context = new CreateFolderContext();
        context.setParentId(userInfoVO.getRootFileId());
        context.setUserId(userId);
        context.setFolderName("folder-name-1");

        Long fileId = userFileService.createFolder(context);
        Assert.notNull(fileId);

        context.setParentId(userInfoVO.getRootFileId());
        context.setUserId(userId);
        context.setFolderName("folder-name-2");

        fileId = userFileService.createFolder(context);
        Assert.notNull(fileId);

        UpdateFilenameContext updateFilenameContext = new UpdateFilenameContext();
        updateFilenameContext.setFileId(fileId);
        updateFilenameContext.setUserId(userId);
        updateFilenameContext.setNewFilename("folder-name-1");

        userFileService.updateFilename(updateFilenameContext);
    }

    /**
     * 测试更新文件名称成功
     */
    @Test
    public void testUpdateFilenameSuccess() {
        Long userId = register();
        UserInfoVo userInfoVO = info(userId);

        CreateFolderContext context = new CreateFolderContext();
        context.setParentId(userInfoVO.getRootFileId());
        context.setUserId(userId);
        context.setFolderName("folder-name-old");

        Long fileId = userFileService.createFolder(context);
        Assert.notNull(fileId);

        UpdateFilenameContext updateFilenameContext = new UpdateFilenameContext();
        updateFilenameContext.setFileId(fileId);
        updateFilenameContext.setUserId(userId);
        updateFilenameContext.setNewFilename("folder-name-new");

        userFileService.updateFilename(updateFilenameContext);
    }

    /**
     * 校验文件删除失败-非法的文件ID
     */
    @Test(expected = BPanBusinessException.class)
    public void testDeleteFileFailByWrongFileId() {
        Long userId = register();
        UserInfoVo userInfoVO = info(userId);

        CreateFolderContext context = new CreateFolderContext();
        context.setParentId(userInfoVO.getRootFileId());
        context.setUserId(userId);
        context.setFolderName("folder-name-old");

        Long fileId = userFileService.createFolder(context);
        Assert.notNull(fileId);

        DeleteFileContext deleteFileContext = new DeleteFileContext();
        List<Long> fileIdList = Lists.newArrayList();
        fileIdList.add(fileId + 1);
        deleteFileContext.setFileIdList(fileIdList);
        deleteFileContext.setUserId(userId);

        userFileService.deleteFile(deleteFileContext);
    }

    /**
     * 校验文件删除失败-非法的用户ID
     */
    @Test(expected = BPanBusinessException.class)
    public void testDeleteFileFailByWrongUserId() {
        Long userId = register();
        UserInfoVo userInfoVO = info(userId);

        CreateFolderContext context = new CreateFolderContext();
        context.setParentId(userInfoVO.getRootFileId());
        context.setUserId(userId);
        context.setFolderName("folder-name-old");

        Long fileId = userFileService.createFolder(context);
        Assert.notNull(fileId);

        DeleteFileContext deleteFileContext = new DeleteFileContext();
        List<Long> fileIdList = Lists.newArrayList();
        fileIdList.add(fileId);
        deleteFileContext.setFileIdList(fileIdList);
        deleteFileContext.setUserId(userId + 1);

        userFileService.deleteFile(deleteFileContext);
    }

    /**
     * 校验用户删除文件成功
     */
    @Test
    public void testDeleteFileSuccess() {
        Long userId = register();
        UserInfoVo userInfoVO = info(userId);

        CreateFolderContext context = new CreateFolderContext();
        context.setParentId(userInfoVO.getRootFileId());
        context.setUserId(userId);
        context.setFolderName("folder-name-old");

        Long fileId = userFileService.createFolder(context);
        Assert.notNull(fileId);

        DeleteFileContext deleteFileContext = new DeleteFileContext();
        List<Long> fileIdList = Lists.newArrayList();
        fileIdList.add(fileId);
        deleteFileContext.setFileIdList(fileIdList);
        deleteFileContext.setUserId(userId);

        userFileService.deleteFile(deleteFileContext);
    }

    private Long register() {
        UserRegisterContext context = createUserRegisterContext();
        Long register = userService.register(context);
        Assert.isTrue(register > 0L);
        return register;
    }

    private UserInfoVo info(Long userId) {
        UserInfoVo userInfoVO = userService.info(userId);
        Assert.notNull(userInfoVO);
        return userInfoVO;
    }

    private final static String USERNAME = "baymax";

    private final static String PASSWORD = "123456789";

    private final static String QUESTION = "question";

    private final static String ANSWER = "answer";

    private UserRegisterContext createUserRegisterContext() {
        UserRegisterContext context = new UserRegisterContext();
        context.setUsername(USERNAME);
        context.setPassword(PASSWORD);
        context.setQuestion(QUESTION);
        context.setAnswer(ANSWER);
        return context;
    }

}
